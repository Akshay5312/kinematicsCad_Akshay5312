import com.neuronrobotics.bowlerstudio.threed.*;
import org.apache.commons.io.*

def base;
//Check if the device already exists in the device Manager
if(args==null){
	base=DeviceManager.getSpecificDevice( "HephaestusArmV2_copy",{
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
