public class OS_Version_Detection {
    public static void main(String[] args) {

        String OS = System.getProperty("os.name").toLowerCase();
        if (OS.contains("linux")) {
            //Linux command
        } else if (OS.contains("win")) {
            //windows command
        }else if (OS.contains("mac")){
            //mac command
        }else if (OS.contains("android")){
            //android command
        }

        System.out.println(System.getProperties());
       System.out.println(System.getProperty("os.version"));

    }
}
