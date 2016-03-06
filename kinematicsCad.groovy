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
	println "Waiting for cad to generate"
	ThreadUtil.wait(50000)// wait for the cad to generate itself
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
println "Waiting for legs to reset"
ThreadUtil.wait(6000)// wait for the legs to fully reset themselves.
println "Now to move one limb" 
// Now we will move just one leg
DHParameterKinematics leg0 = base.getAllDHChains().get(0)
//Start from where the arm already is and move it from there with absolute location
TransformNR current = leg0.getCurrentPoseTarget();
current.translateZ(20);
leg0.setDesiredTaskSpaceTransform(current,  2.0);
ThreadUtil.wait(2000)// wait for the legs to fully arrive
println "Resetting limb"
//and reset it
current.translateZ(-20);
leg0.setDesiredTaskSpaceTransform(current,  2.0);
ThreadUtil.wait(2000)// wait for the legs to fully arrive

println "Now move just one link"
leg0.setDesiredJointAxisValue(1,-25,2.0)// middle link
ThreadUtil.wait(2000)// wait for the link to fully arrive

leg0.setDesiredJointAxisValue(1,0,2.0)// middle link
ThreadUtil.wait(2000)// wait for the link to fully arrive

return null;