public class Caeser {

  public static String encrypt(String orgMessage, int key){

      char[] chars = orgMessage.toCharArray();
      String eMessage="";                        //The encrypted message
      for(char c : chars){                       //Getting the current character in the array
          c+= key;                               //Changing the value of the character by the amount of the key
          eMessage+=c;
      }
      return eMessage;
  }


  public static String decryptCipher(String eMessage, int key){

      String dMessage="";                       //The decrypted message i.e Original message
      char[] cArray =eMessage.toCharArray();

      for(char c: cArray){
          c-= key;
          dMessage+=c;
      }
      return dMessage;
  }


}
