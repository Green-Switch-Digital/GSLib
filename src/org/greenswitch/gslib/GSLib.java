package org.greenswitch.gslib;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.channels.FileChannel;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author acer
 */
public class GSLib {
      static final Logger logger = Logger.getLogger("Logger");
    public void appendFile(String location, String data_to_write){
    try {
			FileWriter writer = new FileWriter(location, true);
                        writer.flush();
                        writer.write(data_to_write);
			writer.close();
		} catch (Exception e) {
                       System.err.println(e.getMessage());
		}
    
    }
    
    public void writeFile(String location, String data_to_write){

        try{
            FileWriter fw = new FileWriter(location, false);
            BufferedWriter bw;
        }catch(Exception e){}
    }
    
    public String readFile(String fileLocation){
    ///This is a very impotant function
        FileReader reader = null;
        try {
            StringBuilder b;
             reader = new FileReader(fileLocation); 
                int character;
                b = new StringBuilder();
                char chha = 0;
                String finalString="";
                while ((character = reader.read()) != -1) {
                    b.append((char)character);
                    
                }
//                        finalString=(String)chha;
//                        System.out.println(b.toString());
            
                        return b.toString();

		} catch (Exception e) {
                        return "";
            	}finally{
            try {
                reader.close();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }

    }
    
    public boolean executeBat(String batFileLocation){
        try {
            String[] command = {"cmd.exe", "/C", "Start", batFileLocation};
            Process p =  Runtime.getRuntime().exec(command);
            return true;
        } catch (IOException ex) {
            return false;
        }
    }
    
    public boolean executeCMD(String command_to_execute){
    try {
            String command = command_to_execute;
            Process process = Runtime.getRuntime().exec(System.getenv("SystemDrive")+"/Windows/System32/cmd.exe "+command);
            return true;
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }
    
    public boolean forceKill(String fileLocation){
    try {
        
        fileLocation=fileLocation.replace("/", "\\");
            String command = "DEL /F "+fileLocation+" \n pause";
            Process process = Runtime.getRuntime().exec(System.getenv("SystemDrive")+"/Windows/System32/cmd.exe "+command);
            return true;
        } catch (IOException ex) {
            return false;
        }
    }
    
        public boolean executeExe(String location){
        Process process;
                try {
                    process = Runtime.getRuntime().exec("\""+location+"\"");
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                    return false;
                }
                return true;
    }
        
        public static String bytesToHex(byte[] hash) {
	    StringBuffer hexString = new StringBuffer();
	    for (int i = 0; i < hash.length; i++) {
	    String hex = Integer.toHexString(0xff & hash[i]);
	    if(hex.length() == 1) hexString.append('0');
	        hexString.append(hex);
	    }
	    return hexString.toString();
	}
        
        public static String getSerial() {
        String result = "";
        GSLib gs = new GSLib();
        try{
        ProcessBuilder builder = new ProcessBuilder("cmd.exe","/c","wmic bios get serialnumber");
        builder.redirectErrorStream(true);
        Process process = builder.start();
        InputStream is = process.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        String line = null;
        while ((line = reader.readLine()) != null) {
//            System.out.println(line);
            result+=line;
        }
        
        }catch(Exception e){}
        return result.replaceAll(" ", "").replace("SerialNumber", "");
    }
        
        public void copy_file(File source, File destination){
            InputStream is = null;
            OutputStream os = null;
            try{
                try {
                    is = new FileInputStream(source);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(GSLib.class.getName()).log(Level.SEVERE, null, ex);
                }
                os = new FileOutputStream(destination);
                byte[] buf = new byte[1024];
                
                int bytesRead;
                while((bytesRead = is.read(buf))>0){
                    os.write(buf,0,bytesRead);
                }
            }catch(Exception e){}
        }
        
        public void copy (File src, File dest,Runnable run) throws IOException {
            FileChannel source_channel = null;
            FileChannel dest_channel = null;
            
            try{
                source_channel = new FileInputStream(src).getChannel();
                dest_channel = new FileOutputStream(dest).getChannel();
                dest_channel.transferFrom(source_channel, 0, source_channel.size());
            }finally{
            source_channel.close();
            dest_channel.close();
            if(run!=null){
                run.run();
            }
            }
        }
       
}

