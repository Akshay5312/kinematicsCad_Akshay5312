import com.neuronrobotics.bowlerstudio.creature.MobileBaseLoader
import com.neuronrobotics.bowlerstudio.scripting.ScriptingEngine
import com.neuronrobotics.bowlerstudio.threed.*;
import com.neuronrobotics.sdk.addons.kinematics.DHParameterKinematics
import com.neuronrobotics.sdk.addons.kinematics.MobileBase
import com.neuronrobotics.sdk.common.DeviceManager

import org.apache.commons.io.*
	
	import com.google.gson.Gson;
	import com.google.gson.GsonBuilder;
	import com.google.gson.reflect.TypeToken;
	import java.lang.reflect.Type;
	
	String myURL = "https://gist.github.com/aa464b39c1208c357b8d4ae7fe210bbb.git"
	String filename = "data.json"
	//GSON parts
	Type TT_mapStringString = new TypeToken<HashMap<String,Object>>() {}.getType();
	Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
	
	HashMap<String,Object> database = new HashMap<String,Object>()
	 
	


MobileBase base;
//Check if the device already exists in the device Manager
if(args==null){
	base=(MobileBase)DeviceManager.getSpecificDevice( "HephaestusArmV2_copy",{
			//If the device does not exist, prompt for the connection
			
			MobileBase m = MobileBaseLoader.fromGit(
				"https://github.com/Akshay5312/HephaestusArmV2_copy.git",
				"HephaestusArmV2_copy.xml"
				)
			if(m==null)
				throw new RuntimeException("Arm failed to assemble itself")
			println "Connecting new device robot arm "+m
			return m
		})
}else
	base=args.get(0)
	
def Limbs = base.getAllDHChains()
def LimbNames =[]
for(DHParameterKinematics limb: Limbs) {
	int NoLinks = limb.numberOfLinks
	def name = limb.getScriptingName()
	def limbRoot = limb.getRobotToFiducialTransform()
	LimbNames.add(name)
	println "name = "+ name + " limbRoot = " + limbRoot 
	
	for(int i = 0; i <NoLinks; i++) {
		def Alpha = limb.getDH_Alpha(i)
		def D = limb.getDH_D(i)
		def R = limb.getDH_R(i)
		def Theta = limb.getDH_Theta(i)
		println "DH Parameters; Alpha = "+Math.toDegrees(Alpha)+" D = "+D+" R = "+R+" Theta = "+Math.toDegrees(Theta)
		
		def LinkDH = []
		LinkDH.add(Alpha)
		LinkDH.add(D)
		LinkDH.add(R)
		LinkDH.add(Theta)		
		database.put(name+"_DH_"+i,LinkDH)
	}
	
	database.put(name+"_size", NoLinks)
}
database.put("Limbs_size", Limbs.size())
database.put("LimbNames", LimbNames)



String writeOut = gson.toJson(database, TT_mapStringString);
println "New database JSON content = \n\n"+writeOut


