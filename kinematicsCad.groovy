import com.neuronrobotics.bowlerstudio.threed.*;
import org.apache.commons.io.*

def base;
//Check if the device already exists in the device Manager
if(args==null){
	base=DeviceManager.getSpecificDevice( "CarlTheWalkingRobot",{
			//If the device does not exist, prompt for the connection
			
			MobileBase m = MobileBaseLoader.fromGit(
				"https://github.com/madhephaestus/carl-the-hexapod.git",
				"CarlTheRobot.xml"
				)
			if(m==null)
				throw new RuntimeException("Arm failed to assemble itself")
			println "Connecting new device robot arm "+m
			return m
		})
}else
	base=args.get(0)


// walk forward 10 increments of 10 mm totalling 100 mm translation
TransformNR move = new TransformNR(10,0,0,new RotationNR())
double toSeconds=0.1//100 ms for each increment
for(int i=0;i<10;i++){
	base.DriveArc(move, toSeconds);
	ThreadUtil.wait((int)toSeconds*1000)
}
// turn 20 increments of 2 degrees totalling 40 degrees turn
move = new TransformNR(0,0,0,new RotationNR( 0,0, 2))
for(int i=0;i<20;i++){
	base.DriveArc(move, toSeconds);
	ThreadUtil.wait((int)toSeconds*1000)
}
// walk sideways 10 increments of 10 mm totalling 100 mm translation
move = new TransformNR(0,10,0,new RotationNR())
for(int i=0;i<10;i++){
	base.DriveArc(move, toSeconds);
	ThreadUtil.wait((int)toSeconds*1000)
}

println "Waiting for legs to reset"
ThreadUtil.wait(12000)// wait for the legs to fully reset themselves.
println "Now to move one limb" 
// Now we will move just one leg
DHParameterKinematics leg0 = base.getAllDHChains().get(0)
double zLift=5
//Start from where the arm already is and move it from there with absolute location
TransformNR current = leg0.getCurrentPoseTarget();
current.translateZ(zLift);
leg0.setDesiredTaskSpaceTransform(current,  2.0);
ThreadUtil.wait(2000)// wait for the legs to fully arrive

//and reset it
current.translateZ(-zLift);
leg0.setDesiredTaskSpaceTransform(current,  2.0);
ThreadUtil.wait(2000)// wait for the legs to fully arrive

current.translateX(zLift);
leg0.setDesiredTaskSpaceTransform(current,  2.0);
ThreadUtil.wait(2000)// wait for the legs to fully arrive
//and reset it
current.translateX(-zLift);
leg0.setDesiredTaskSpaceTransform(current,  2.0);
ThreadUtil.wait(2000)// wait for the legs to fully arrive

current.translateY(-zLift);
leg0.setDesiredTaskSpaceTransform(current,  2.0);
ThreadUtil.wait(2000)// wait for the legs to fully arrive
//and reset it
current.translateY(zLift);
leg0.setDesiredTaskSpaceTransform(current,  2.0);
ThreadUtil.wait(2000)// wait for the legs to fully arrive

println "Now move just one link"
for(int i=0;i<leg0.getNumberOfLinks();i++){
	leg0.setDesiredJointAxisValue(i,// link index
							25, //target angle
							2.0) // 2 seconds
	ThreadUtil.wait(2000)// wait for the link to fully arrive
	
	leg0.setDesiredJointAxisValue(i,// link index
							0, //target angle
							2.0) // 2 seconds
	ThreadUtil.wait(2000)// wait for the link to fully arrive
}
return null;