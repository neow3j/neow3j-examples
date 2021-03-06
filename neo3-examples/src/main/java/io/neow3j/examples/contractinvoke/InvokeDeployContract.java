package io.neow3j.examples.contractinvoke;

import io.neow3j.contract.FungibleToken;
import io.neow3j.contract.ScriptHash;
import io.neow3j.protocol.Neow3j;
import io.neow3j.protocol.core.methods.response.NeoSendRawTransaction;
import io.neow3j.protocol.http.HttpService;
import io.neow3j.transaction.Signer;
import io.neow3j.wallet.Account;
import io.neow3j.wallet.Wallet;

public class InvokeDeployContract {

    public static void main(String[] args) throws Throwable {
        // Set up the connection to the neo-node.
        Neow3j neow3j = Neow3j.build(new HttpService("http://localhost:40332"));
        
        // The main account available when starting a bockchain from `deault.neo-express`.
        Account account = Account.fromWIF("L3kCZj6QbFPwbsVhxnB8nUERDy4mhCSrWJew4u5Qh5QmGMfnCTda");
        Wallet wallet = Wallet.withAccounts(account);

        // Setup a wrapper to invoke the contract.
        FungibleToken contract = new FungibleToken(
            new ScriptHash("fd64cd9e7bd4289e9e0c766153e3bc75235c72e2"), neow3j);

        // Invoke the contract's deploy method, creating a raw transaction, signing, and sending it. 
        NeoSendRawTransaction response = contract.invokeFunction("deploy")
            .signers(Signer.calledByEntry(account.getScriptHash()))
            .wallet(wallet)
            .sign()
            .send();

        // Print the transaction hash
        if (!response.hasError()) {
            System.out.println("Transaction Hash: " + response.getResult().getHash());
        } else {
            System.out.println("Error: " + response.getError().getMessage());
        }

    }
}
