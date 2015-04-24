//Create the kinematics model from the xml file describing the D-H compliant parameters. 
DHParameterKinematics model = new DHParameterKinematics(dyio,"TrobotMaster.xml");

//Creating a list of objects, one for each link
ArrayList<Object> links = new ArrayList<Object>();
ArrayList<Affine> links = new ArrayList<Affine>();
for(DHLink dh : model.getDhChain().getLinks() ){
	System.out.println("Link D-H values = "+dh);
	// Create an axis to represent the link
	CSG cube = new Cube(20).toCSG();
	Affine l = new Affine();
	//add listner to axis
	cube.setManipulator(l);
	// add ax to list of objects to be returned
	links.add(cube);
}

model.addPoseUpdateListener(new ITaskSpaceUpdateListenerNR() {
			@Override
			public void onTaskSpaceUpdate(AbstractKinematicsNR source,	final TransformNR p) {
				final ArrayList<TransformNR> jointLocations = model.getChainTransformations();
				for (int i = 0; i < links.size(); i++) {
					// setting the current location of each joint
					TransformFactory.getTransform(jointLocations.get(i), links.get(i));
				}
			}
			@Override public void onTargetTaskSpaceUpdate(AbstractKinematicsNR source,TransformNR pose) {}
		});

return links;