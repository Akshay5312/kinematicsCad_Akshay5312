import com.neuronrobotics.bowlerstudio.creature.CreatureLab;
import org.apache.commons.io.IOUtils;

//Create the kinematics model from the xml file describing the D-H compliant parameters. 
String xmlContent = ScriptingEngineWidget.codeFromGistID("2b0cff20ccee085c9c36","TrobotLinks.xml")[0];
MobileBase mb = new MobileBase(IOUtils.toInputStream(xmlContent, "UTF-8"));
DHParameterKinematics model = mb.getAppendages().get(0); 
//Creating a list of objects, one for each link
ArrayList<Object> links = new ArrayList<Object>();
model.setScriptingName("DHArm")
links.add(mb);
for(DHLink dh : model.getDhChain().getLinks() ){
	System.out.println("Link D-H values = "+dh);
	// Create an axis to represent the link
	CSG cube = new Cube(20).toCSG();
	//add listner to axis
	cube.setManipulator(dh.getListener());
	// add ax to list of objects to be returned
	links.add(cube);
}
 
return links;