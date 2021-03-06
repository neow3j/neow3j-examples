package io.neow3j.examples.contractinvoke;

import java.math.BigDecimal;
import io.neow3j.contract.FungibleToken;
import io.neow3j.contract.ScriptHash;
import io.neow3j.protocol.Neow3j;
import io.neow3j.protocol.core.methods.response.NeoSendRawTransaction;
import io.neow3j.protocol.http.HttpService;
import io.neow3j.transaction.Transaction;
import io.neow3j.wallet.Account;
import io.neow3j.wallet.Wallet;

public class InvokeTransferContract {

    public static void main(String[] args) throws Throwable {
        // Set up the connection to the neo-node.
        Neow3j neow3j = Neow3j.build(new HttpService("http://localhost:40332"));
        // The main account available when starting a bockchain from `deault.neo-express`.
        Account account = Account.fromWIF("L3kCZj6QbFPwbsVhxnB8nUERDy4mhCSrWJew4u5Qh5QmGMfnCTda");
        Wallet wallet = Wallet.withAccounts(account);

        // Setup a wrapper to invoke the contract.
        FungibleToken contract = new FungibleToken(
            new ScriptHash("fd64cd9e7bd4289e9e0c766153e3bc75235c72e2"), neow3j);

        // Invoke by creating a raw transaction, signing, and sending it. 
        Transaction tx = contract.transfer(
                wallet,
                // ScriptHash: d6c712eb53b1a130f59fd4e5864bdac27458a509
                // Address: NLnyLtep7jwyq1qhNPkwXbJpurC4jUT8ke
                new ScriptHash("d6c712eb53b1a130f59fd4e5864bdac27458a509"), // to
                new BigDecimal("1")) // amount
            .sign();
        NeoSendRawTransaction response = tx.send();

        if (!response.hasError()) {
            System.out.println("Transaction sent successfully - start tracking.");
            tx.track().subscribe(blockIndex -> {
                String state = tx.getApplicationLog().getExecutions().get(0).getState();
                System.out.printf("Found the transaction on block %s. Invocation exited with state %s.\n", blockIndex, state);
                neow3j.shutdown();
            });
        } else {
            System.out.println("Error: " + response.getError().getMessage());
        }

    }
}
