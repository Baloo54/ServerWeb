/**
 * classe permettant de filtrer les ip
 * @author Comte Gabriel
 * @version 1.0
 */

 public class FilterIP {
    /**
     * attribut ip a tester
     */
    String ipTest;
    /**
     * constructeur 
     * @param ipTest
     */
    public FilterIP(String ipTest) {
        if(ipTest == null) throw new IllegalArgumentException("IP must not be null");
        // Convertir l'adresse IP en binaire pour faciliter la comparaison
        String[] octets = ipTest.split("\\.");
        StringBuilder binaryIP = new StringBuilder();
        for (String octet : octets) {
            int decimal = Integer.parseInt(octet);
            String binary = Integer.toBinaryString(decimal);
            binaryIP.append(String.format("%8s", binary).replace(' ', '0'));
        }
        this.ipTest = binaryIP.toString();
    }
    /**
     * méthode passerIP
     * @param ip
     * @return boolean
     * permet de tester si l'ip est autorisée
     * retourne true si l'ip est autorisée
     */
    public boolean passIP(String ip) {
        if(ip == null) throw new IllegalArgumentException("IP must not be null");
        int cache = Integer.parseInt(ip.split("/")[1]);
        // Convertir l'adresse IP en binaire pour faciliter la comparaison
        String[] octets = ip.split("/")[0].split("\\.");
        StringBuilder binaryIP = new StringBuilder();
        for (String octet : octets) {
            int decimal = Integer.parseInt(octet);
            String binary = Integer.toBinaryString(decimal);
            binaryIP.append(String.format("%8s", binary).replace(' ', '0'));
        }
        return binaryIP.toString().substring(0, cache).equals(this.ipTest.substring(0, cache));
    }
}