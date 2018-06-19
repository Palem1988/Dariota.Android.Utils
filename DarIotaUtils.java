import android.app.Activity;
import android.location.Location;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import jota.IotaAPI;
import jota.model.Transaction;
import jota.utils.TrytesConverter;

public class DarIotaUtils {
    
    public static IotaAPI api = new IotaAPI.Builder().protocol("https").host("field.carriota.com").port("443").build();

    public static String locationToAddress(Location lastLocation) {
        String latString = "" + lastLocation.getLatitude();
        String lonString = "" + lastLocation.getLongitude();
        int tempPos1 = latString.indexOf(".");
        int tempPos2 = lonString.indexOf(".");
        String tempString1 = latString.substring(0, tempPos1 + 2);
        String tempString2 = lonString.substring(0, tempPos2 + 2);
        String locationAddress = tempString1 + " " + tempString2;
        locationAddress = locationAddress.replace("0", "A");
        locationAddress = locationAddress.replace("1", "B");
        locationAddress = locationAddress.replace("2", "C");
        locationAddress = locationAddress.replace("3", "D");
        locationAddress = locationAddress.replace("4", "E");
        locationAddress = locationAddress.replace("5", "F");
        locationAddress = locationAddress.replace("6", "G");
        locationAddress = locationAddress.replace("7", "H");
        locationAddress = locationAddress.replace("8", "I");
        locationAddress = locationAddress.replace("9", "J");
        locationAddress = locationAddress.replace(".", "K");
        locationAddress = locationAddress.replace("-", "L");
        locationAddress = locationAddress.replace("+", "M");
        locationAddress = locationAddress.replace(" ", "N");
        int tempLength = locationAddress.length();
        int padLength = 81 - tempLength;
        int i = 0;
        while (i < padLength) {
            locationAddress = locationAddress + "Z";
            i++;
        }
        return locationAddress;
    }

    /*
         String newMessage = DarIotaUtils.getConvertedData(transaction.getSignatureFragments());
         String[] items = newMessage.split("\\|");
         Location location = DarIotaUtils.getLocationFromIotaAddy(items[1]);
     */
    public static Location getLocationFromIotaAddy(String data) {
        String[] locs = data.split(" ");
        Location location = new Location("iota");
        location.setLongitude(Double.parseDouble(locs[1]));
        location.setLatitude(Double.parseDouble(locs[0]));
        location.setAltitude(0);
        location.setBearing(0);
        location.setAccuracy(99);
        location.setTime(System.currentTimeMillis());
        location.setSpeed(1);
        return location;
    }

    /*
        Pass in transaction.getSignatureFragments()
     */
    public static String getCleanedData(String resultMessage) {
        int tempEnd = resultMessage.length();
        int countPos = resultMessage.length();
        String newMessage = "";
        while (countPos > 0) {
            if (resultMessage.substring(tempEnd - 1, tempEnd).equals("9")) {
                // update tempEnd
                tempEnd = tempEnd - 1;
            } else {
                // found all 999's
                newMessage = resultMessage.substring(0, countPos);
                break;
            }
            countPos--;
        }
        return newMessage;
    }

    /*
        Pass in transaction.getSignatureFragments()
     */
    public static String getConvertedData(String resultMessage) {
        int tempEnd = resultMessage.length();
        int countPos = resultMessage.length();
        String newMessage = "";
        while (countPos > 0) {
            if (resultMessage.substring(tempEnd - 1, tempEnd).equals("9")) {
                // update tempEnd
                tempEnd = tempEnd - 1;
            } else {
                // found all 999's
                newMessage = resultMessage.substring(0, countPos);
                break;
            }
            countPos--;
        }
        //Response Example : CHAT|40.7127753 -74.0059728|06-16-2018|AAAAAAAAAAAAAAAAAAAAAAAAAAA||Hello
        return TrytesConverter.toString(newMessage);
    }

    public class AddIotaAsyncTask extends AsyncTask<String, String, String> {
        Activity act;

        public AddIotaAsyncTask(Activity act) {
            this.act = act;
        }

        @Override
        protected String doInBackground(String... params) {
            String result = "";

            try {
                String[] addressData = {DarIotaUtils.locationToAddress(lastLocation)};
                List<Transaction> iotaTransactions = null;
                iotaTransactions = DarIotaUtils.api.findTransactionObjectsByAddresses(addressData);

                if (iotaTransactions != null) {
                    List<Transaction> dariotaTransactions = new ArrayList<>();
                    for (Transaction transaction : iotaTransactions) {
                        if (!TextUtils.equals(transaction.getSignatureFragments(), "999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999")) {
                            dariotaTransactions.add(transaction);

                            //Do something
//                            String newMessage = IotaApi.getConvertedData(transaction.getSignatureFragments());
//                            String[] items = newMessage.split("\\|");
//                            Location location = IotaApi.getLocationFromIotaAddy(items[1]);
                        }
                    }
                }
            } catch (Exception e) {
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.d(Constants.TAG, "iota ingestion complete");
        }
    }
}
