package assignment;

import java.io.*;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Assignment{
private String username, password,logintime, dob, name, gender;  
private String room,customer, IC, Contact,Email, bookingtime;
private String checkin, checkout;
private int Numdate = 0;

public Assignment(String LoginName, String LoginPassword, String FormattedDateTime){
    this.username = LoginName;
    this.password = LoginPassword;
    this.logintime = FormattedDateTime;
}

public Assignment(){
}

public Assignment(String RegisterUsername, String RegisterPassword, String RegisterDOB, String RegisterName, String RegisterGender){
    this.username = RegisterUsername;
    this.password = RegisterPassword;
    this.dob = RegisterDOB;
    this.name = RegisterName;
    this.gender = RegisterGender;
}

public Assignment(String roomid,String customername, String ic, String email, String contact, String Checkin, String Checkout){
    this.room = roomid;
    this.customer = customername;
    this.IC = ic;
    this.Email = email;
    this.Contact = contact;
    this.checkin = Checkin;
    this.checkout = Checkout;
}

public Assignment(String Checkin, String Checkout){
    this.checkin = Checkin;
    this.checkout = Checkout;
}

public String RegisterAccount() {
    try {
        int resvalue = 0;
        FileWriter fw = new FileWriter("account.txt", true);
        if (!"".equals(username) && !"".equals(password) && !"".equals(name) && gender != null && dob != null) {
            String[] dobs = dob.split("/");
            if (dobs.length == 3 && !dobs[0].equals("-Date-") && !dobs[1].equals("-Month-") && !dobs[2].equals("-Year-")) {
                try (PrintWriter outputFile = new PrintWriter(fw)) {
                    outputFile.print(username);
                    outputFile.print(", ");
                    outputFile.print(password);
                    outputFile.print(", ");
                    outputFile.print(name);
                    outputFile.print(", ");
                    outputFile.print(gender);
                    outputFile.print(", ");
                    outputFile.print(dob);
                    outputFile.println(", ");
                    outputFile.close();
                    resvalue = 1;
                }
            }
        }
        if (resvalue == 1) {
            return "Success";
        } else {
            return "Register details cannot be empty!";
        }
    } catch (IOException ex) {
        return ex.toString();
    }
}

public boolean LoginAccount(){
    try{
        int logvalue = 0;
        File Login = new File("account.txt");
        Scanner inputfile = new Scanner(Login);
        while(inputfile.hasNextLine()){
            String[] list = inputfile.nextLine().split(", ");
            String fileUsername = list[0];
            String filePassword = list[1];
            if (fileUsername.equals(username) && filePassword.equals(password)) {
                logvalue = 1;
            }
        }
        if (logvalue == 1){
            return true;
        }else{
            return false;
        }
        
    }catch(FileNotFoundException ex){
        return false;
    }
}

public String getreturn(){
    return "Login Sucess";
}

public String Booking(String Checkins, String Checkouts){ 
    try{
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        Date checkindate = null;
        try {
            checkindate = dateFormat.parse(Checkins);
        } catch (ParseException ex) {
            Logger.getLogger(Assignment.class.getName()).log(Level.SEVERE, null, ex);
        }
        Date checkoutdate = null;
        try {
            checkoutdate = dateFormat.parse(Checkouts);
        } catch (ParseException ex) {
            Logger.getLogger(Assignment.class.getName()).log(Level.SEVERE, null, ex);
        }

        long milli = checkoutdate.getTime() - checkindate.getTime();
        int Numdate = (int) (milli / (1000 * 60 * 60 * 24));

        int bookvalue = 0;
        FileWriter bk = new FileWriter("booking.txt", true);
        if (!"".equals(room) && !"".equals(customer) && !"".equals(IC) && !"".equals(Email) && !"".equals(Contact) && !"".equals(bookingtime)) {
            try (PrintWriter outputFiles = new PrintWriter(bk)) {
                outputFiles.print(room);
                outputFiles.print(", ");
                outputFiles.print(customer);
                outputFiles.print(", ");
                outputFiles.print(IC);
                outputFiles.print(", ");
                outputFiles.print(Email);
                outputFiles.print(", ");
                outputFiles.print(Contact);
                outputFiles.print(", ");
                outputFiles.print(Numdate);
                outputFiles.print(", ");
                outputFiles.print(Checkins);
                outputFiles.print(", ");
                outputFiles.print(Checkouts);
                outputFiles.println(", ");
                bookvalue = 1;
            }
        }
        bk.close(); // close the FileWriter here
        if (bookvalue == 1) {
            return "Success";
        } else {
            return "booking details cannot be empty!";
        }
    } catch (IOException ex) {
        return ex.toString();       
    }
}

public void SaveReceipt(){
    String [] readvalue = null;
    File readfile = new File("booking.txt");
    Scanner bookingfile;
    try {
        bookingfile = new Scanner(readfile);
        while(bookingfile.hasNextLine()){
            readvalue = bookingfile.nextLine().split(", ");
        }
        String getroomid = readvalue[0];
        String getname = readvalue[1];
        String getic = readvalue[2];
        String getnumday = readvalue[5];
        String getcheckin = readvalue[6];
        String getcheckout = readvalue[7];
        
        int amountday = Integer.parseInt(getnumday);
        
        int countamount = (int) (amountday *((350* 1.1) + 10));
        String getamount = String.valueOf(countamount);
        
        
        try {
            String [] readreceiptidvalue = null;
            int loopingtime = 0;
            File readreceiptfile = new File("receipt.txt");
            Scanner receiptfile = new Scanner(readreceiptfile);
            while (receiptfile.hasNextLine()) {
                String eachline = receiptfile.nextLine();
                readreceiptidvalue = eachline.split(", ");
                loopingtime = 1;
            }
            if (loopingtime == 0){
                readreceiptidvalue = new String[]{"0"};
            }
            String lastreceiptid = readreceiptidvalue[0];
            int countreceiptid = Integer.parseInt(lastreceiptid);
            int latestreceipt = countreceiptid + 1;
            String getreceiptid = String.valueOf(latestreceipt);
            FileWriter filereceipt;
            try {
                filereceipt = new FileWriter("receipt.txt", true);
                PrintWriter writeinfile = new PrintWriter(filereceipt);
                writeinfile.print(getreceiptid);
                writeinfile.print(", ");
                writeinfile.print(getroomid);
                writeinfile.print(", ");
                writeinfile.print(getname);
                writeinfile.print(", ");
                writeinfile.print(getic);
                writeinfile.print(", ");
                writeinfile.print(getcheckin);
                writeinfile.print(", ");
                writeinfile.print(getcheckout);
                writeinfile.print(", ");
                writeinfile.print(getamount);
                writeinfile.println(", ");
                writeinfile.close();
            } catch (IOException ex) {
                Logger.getLogger(Assignment.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Assignment.class.getName()).log(Level.SEVERE, null, ex);
        }  
    } catch (FileNotFoundException ex) {
        Logger.getLogger(Assignment.class.getName()).log(Level.SEVERE, null, ex);
    }  
}

public String BookingDate(){
    
    try {
      SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
      Date checkinDate = sdf.parse(checkin);
      Date checkoutDate = sdf.parse(checkout);
      
      if (checkoutDate.before(checkinDate)||checkoutDate.equals(checkinDate)) {
        return"Must at least 1 day";
      }
      
      Calendar startCal = Calendar.getInstance();
      startCal.setTime(checkinDate);
      Calendar endCal = Calendar.getInstance();
      endCal.setTime(checkoutDate);
      
      while (startCal.before(endCal)) {
        Numdate++;
        startCal.add(Calendar.DAY_OF_MONTH, 1);
      }
      return"Sucess";
    } catch (ParseException e) {
      return"System Error";
    }
}

public String CheckAvailableroom(String Checkin, String Checkout) {
    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
    Date checkinDate, checkoutDate;
    try {
        checkinDate = dateFormat.parse(Checkin);
        checkoutDate = dateFormat.parse(Checkout);
    } catch (ParseException e) {
        return "Invalid date format";
    }

    List<String> availableRooms = new ArrayList<>();
    // read all rooms from room.txt
    try (BufferedReader roomReader = new BufferedReader(new FileReader("room.txt"))) {
        String line;
        while ((line = roomReader.readLine()) != null) {
            String[] roomData = line.split(", ");
            String roomId = roomData[0];
            availableRooms.add(roomId);
        }
    } catch (IOException e) {
        return "Error reading room data";
    }

    // remove rooms that have bookings during the given time period
    try (BufferedReader bookingReader = new BufferedReader(new FileReader("booking.txt"))) {
        String line;
        while ((line = bookingReader.readLine()) != null) {
            String[] bookingData = line.trim().split(", ");
            String roomId = bookingData[0];
            Date bookingCheckin = dateFormat.parse(bookingData[6]);
            Date bookingCheckout = dateFormat.parse(bookingData[7]);

            // check if the room has a booking during the given time period
            if (roomId != null && availableRooms.contains(roomId)) {
                if (!((checkinDate.before(bookingCheckin) && checkoutDate.before(bookingCheckin))
                        || (checkinDate.after(bookingCheckout) && checkoutDate.after(bookingCheckout)))) {
                    availableRooms.remove(roomId);
                }
            }
        }
    } catch (IOException | ParseException e) {
        return "Error reading booking data";
    }

    if (availableRooms.isEmpty()) {
        return "Empty";
    } else {
        return String.join(", ", availableRooms);
    }
}


}
class False extends Assignment{
    
    @Override
    public String getreturn() {
        return "wrong username or password";
    }
}
