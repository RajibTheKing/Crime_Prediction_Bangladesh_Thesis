

public class AddRules {

    public void AddRules() {
    }
    String v = "VM";
    String n = "NN";
    String adj = "ADJ";

    public String checkRules(String stemPart, String PoS) {
        if (stemPart == "আন" && PoS == v) {
            return v;
        } else if (stemPart == "আন" && PoS == n) {
            return v;
        } else if (stemPart == "নী" && PoS == n) {
            return n;
        } else if (stemPart == "নি" && PoS == n) {
            return n;
        } else if (stemPart == "অনি" && PoS == n) {
            return n;
        } else if (stemPart == "আনী" && PoS == n) {
            return n;
        } else if (stemPart == "ইতি" && PoS == n) {
            return n;
        } else if (stemPart == "উনি" && PoS == n) {
            return n;
        } else if (stemPart == "উন" && PoS == n) {
            return n;
        } else if (stemPart == "ভর" && PoS == n) {
            return adj;
        } else if (stemPart == "ভরা" && PoS == n) {
            return adj;
        } else if (stemPart == "আ" && PoS == n) {
            return n;
        } else if (stemPart == "আ" && PoS == n) {
            return adj;
        } else if (stemPart == "আ" && PoS == v) {
            return v;
        } else if (stemPart == "ই" && PoS == n) {
            return adj;
        } else if (stemPart == "ঈ" && PoS == n) {
            return adj;
        } else if (stemPart == "ই" && PoS == n) {
            return v;
        } else if (stemPart == "লী" && PoS == n) {
            return adj;
        } else if (stemPart == "ইয়ে" && PoS == adj) {
            return adj;
        } else if (stemPart == "ইয়ে" && PoS == adj) {
            return n;
        } else if (stemPart == "ইল" && PoS == v) {
            return v;
        } else if (stemPart == "ল" && PoS == v) {
            return v;
        } else if (stemPart == "আনো" && PoS == v) {
            return v;
        } else if (stemPart == "ই" && PoS == v) {
            return n;
        } else if (stemPart == "ই" && PoS == v) {
            return adj;
        } else if (stemPart == "ই" && PoS == n) {
            return n;
        } else if (stemPart == "ই" && PoS == adj) {
            return n;
        } else if (stemPart == "ই" && PoS == adj) {
            return v;
        } else {
            return PoS;
        }


    }
}
