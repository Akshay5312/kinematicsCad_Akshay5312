import com.neuronrobotics.bowlerstudio.creature.CreatureLab;
import org.apache.commons.io.IOUtils;

MobileBase base;
Object dev = DeviceManager.getSpecificDevice(MobileBase.class, "CarlTheWalkingRobot");
println "found: "+dev
//Check if the device already exists in the device Manager
if(dev==null){
	//Create the kinematics model from the xml file describing the D-H compliant parameters. 
	def file=["https://gist.github.com/bcb4760a449190206170.git","CarlTheRobot.xml"]as String[]
	String xmlContent = ScriptingEngine.codeFromGit(file[0],file[1])[0]
	MobileBase mb =new MobileBase(IOUtils.toInputStream(xmlContent, "UTF-8"))
	mb.setGitSelfSource(file)
	mb.connect()
	DeviceManager.addConnection(mb,mb.getScriptingName())
	base=mb
	ThreadUtil.wait(10000)// wait for the cad to generate itself
}else{
	println "Robot found, runing code"
	//the device is already present on the system, load the one that exists.
  	base=(MobileBase)dev
}

// walk forward 10 increments 
TransformNR move = new TransformNR(10,0,0,new RotationNR())
double toSeconds=0.1//100 ms for each increment
for(int i=0;i<10;i++){
	base.DriveArc(move, toSeconds);
	ThreadUtil.wait((int)toSeconds*1000)
}

ThreadUtil.wait(6000)// wait for the legs to fully reset themselves. 



return null;