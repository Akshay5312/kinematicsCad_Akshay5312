import com.neuronrobotics.bowlerstudio.creature.MobileBaseLoader
import com.neuronrobotics.bowlerstudio.threed.*;
import com.neuronrobotics.sdk.addons.kinematics.DHParameterKinematics
import com.neuronrobotics.sdk.addons.kinematics.MobileBase
import com.neuronrobotics.sdk.common.DeviceManager

import org.apache.commons.io.*

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
	
for(DHParameterKinematics limb: Limbs) {
	int NoLimbs = limb.numberOfLinks
	def name = limb.getScriptingName()
	def limbRoot = limb.getRobotToFiducialTransform()
	println "name = "+ name + " limbRoot = " + limbRoot 
	for(int i = 0; i <NoLimbs; i++) {
		def Alpha = limb.getDH_Alpha(i)
		def D = limb.getDH_D(i)
		def R = limb.getDH_R(i)
		def Theta = limb.getDH_Theta(i)
		println "DH Parameters; Alpha = "+Math.toDegrees(Alpha)+" D = "+D+" R = "+R+" Theta = "+Math.toDegrees(Theta)
	}
}
