import com.neuronrobotics.bowlerstudio.creature.CreatureLab;
import org.apache.commons.io.IOUtils;

MobileBase base;
Object dev = DeviceManager.getSpecificDevice(MobileBase.class, "TrobotArmGroup");
println "found: "+dev
//Check if the device already exists in the device Manager
if(dev==null){
	//Create the kinematics model from the xml file describing the D-H compliant parameters. 
	String xmlContent = ScriptingEngine.codeFromGistID("2b0cff20ccee085c9c36","TrobotLinks.xml")[0];
	return new MobileBase(IOUtils.toInputStream(xmlContent, "UTF-8"));
}else{
	println "Arm found, runing code"
	//the device is already present on the system, load the one that exists.
  	base=(MobileBase)dev
}

DHParameterKinematics model = base.getAppendages().get(0); 
// Use Creature Lab to set the CAD script and customize your model. 

return null;