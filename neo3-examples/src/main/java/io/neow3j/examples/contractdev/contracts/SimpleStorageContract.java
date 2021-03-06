package io.neow3j.examples.contractdev.contracts;

import io.neow3j.devpack.Storage;
import io.neow3j.devpack.annotations.ManifestExtra;

// A simple contract that allows putting and getting from it's storage area.
@ManifestExtra(key = "name", value = "SimpleStorageContract")
public class SimpleStorageContract {

    public static boolean putSomething(String key, String value) {
        Storage.put(key, value);
        return true;
    }

    public static byte[] getSomething(String key) {
        return Storage.get(key);
    }
}
