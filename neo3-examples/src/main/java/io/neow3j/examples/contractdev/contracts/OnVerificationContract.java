package io.neow3j.examples.contractdev.contracts;

import static io.neow3j.devpack.StringLiteralHelper.addressToScriptHash;

import io.neow3j.devpack.Hash160;
import io.neow3j.devpack.Runtime;
import io.neow3j.devpack.annotations.DisplayName;
import io.neow3j.devpack.annotations.ManifestExtra;
import io.neow3j.devpack.annotations.OnNEP17Payment;
import io.neow3j.devpack.annotations.OnVerification;
import io.neow3j.devpack.events.Event1Arg;
import io.neow3j.devpack.events.Event3Args;

@ManifestExtra(key = "name", value = "OnVerificationContract")
@ManifestExtra(key = "author", value = "AxLabs")
public class OnVerificationContract {

    static final Hash160 owner = addressToScriptHash("NZNos2WqTbu5oCgyfss9kUJgBXJqhuYAaj");

    @DisplayName("onPayment")
    static Event3Args<Hash160, Integer, Object> onPayment;

    @DisplayName("onVerification")
    static Event1Arg<String> onVerification;

    @OnVerification
    public static boolean verify() throws Exception {
        if (!Runtime.checkWitness(owner)) {
            throw new Exception("The calling entity is not the owner of this contract.");
        }
        onVerification.notify("It's the owner!");
        return true;
    }

    @OnNEP17Payment
    public static void onPayment(Hash160 from, int amount, Object data) {
        onPayment.notify(from, amount, data);
    }

}
