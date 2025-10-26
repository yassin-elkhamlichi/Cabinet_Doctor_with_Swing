package cabinetdoctor.Controles;
public class BDInfo {
	protected static String bdName = "cabinetdoctor";
	protected static String protocol = "jdbc:mysql:";
	protected static String user = "your user name";
	protected static String password = "****";
	protected static String ip = "127.0.0.1";
	protected static int port = 3306;
	protected static String url = protocol + "//" + ip + ":" + port + "/" + bdName;
        protected static String filesPath = System.getProperty("user.dir") + "/src/cabinetdoctor/Files/";
        protected static String photoPath = System.getProperty("user.dir") + "/src/cabinetdoctor/photo/cabinet.jpg";
}
