import com.idohoo.user.model.User;
import com.idohoo.user.protocol.resq.UserMsResq;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


public class VoSetTest {

	public static void printSetMethod(Object o,String prex,String targetName) throws IllegalArgumentException, IllegalAccessException{
		 //得到类对象
	      Class userCla = (Class) o.getClass();
	      Method[] methods = userCla.getMethods();
	       for(int i = 0; i < methods.length; i++){
	           Method method = methods[i];
	           if(method.getName().startsWith("set")){
	        	  String getMethodName = "g"+method.getName().substring(1);
	              System.out.println(prex+"."+method.getName()+"("+targetName+"."+getMethodName+"());");
	           }
	       }
	}
	public static void printSetOnlyMethod(Object o,String prex) throws IllegalArgumentException, IllegalAccessException{
		 //得到类对象
	      Class userCla = (Class) o.getClass();
	      Method[] methods = userCla.getMethods();
	       for(int i = 0; i < methods.length; i++){
	           Method method = methods[i];
	           if(method.getName().startsWith("set")){
	        	  String getMethodName = "g"+method.getName().substring(1);
	              System.out.println(prex+"."+method.getName()+"();");
	           }
	       }
	}
	public static void printName(Object o) throws IllegalArgumentException, IllegalAccessException{
		 //得到类对象
	      Class userCla = (Class) o.getClass();
	      Field[] methods = userCla.getDeclaredFields();
	       for(int i = 0; i < methods.length; i++){
	    	   Field method = methods[i];
	    	   String type = method.getType().getName();
	    	   String name = method.getName();
	    	   System.out.println(type+" "+name+";");
	       }
	}

	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException {

		printSetMethod(new UserMsResq(),"userMsResq","user");

	}
}
