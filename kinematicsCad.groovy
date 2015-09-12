import com.neuronrobotics.bowlerstudio.creature.CreatureLab;
import org.apache.commons.io.IOUtils;

//Create the kinematics model from the xml file describing the D-H compliant parameters. 
String xmlContent = ScriptingEngineWidget.codeFromGistID("2b0cff20ccee085c9c36","TrobotLinks.xml")[0];
MobileBase base = new MobileBase(IOUtils.toInputStream(xmlContent, "UTF-8"));
DHParameterKinematics model = base.getAppendages().get(0); 
// Use Creature Lab to set the CAD script and customize your model. 

return base;