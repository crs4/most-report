package it.crs4.most.ehrlib.test;

import it.crs4.most.ehrlib.ArchetypeSchemaProvider;
import android.content.Context;
import android.test.InstrumentationTestCase;
import android.util.Log;

public class TestArchetypeSchemaProvider  extends InstrumentationTestCase {

	private static final String TAG = "TestArchetypeSchemaProvider";
	private Context context;
	private ArchetypeSchemaProvider asp;
    private static final String BLOOD_PRESSURE_ARCHETYPE_CLASS = "openEHR-EHR-OBSERVATION.blood_pressure.v1";
    private static final String ECG_ARCHETYPE_CLASS = "openEHR-EHR-OBSERVATION.ecg.v1";
    
	protected void setUp() {
		 Log.d(TAG,"Running testCase");
		 context = getInstrumentation().getTargetContext().getApplicationContext(); 
		 assertNotNull(context);
		 
		 asp = new ArchetypeSchemaProvider(context, "archetypes.properties", "archetypes");
	}
	
	public void testGetBloodPressureSchemas()
	{
		assertNotNull(asp.getLayoutSchema(BLOOD_PRESSURE_ARCHETYPE_CLASS));
		assertNotNull(asp.getOntologySchema(BLOOD_PRESSURE_ARCHETYPE_CLASS));
		assertNotNull(asp.getDatatypesSchema(BLOOD_PRESSURE_ARCHETYPE_CLASS));
		assertNotNull(asp.getAdlStructureSchema(BLOOD_PRESSURE_ARCHETYPE_CLASS));
	}
	
	public void testGetECGSchemas()
	{
		assertNotNull(asp.getLayoutSchema(ECG_ARCHETYPE_CLASS));
		assertNotNull(asp.getOntologySchema(ECG_ARCHETYPE_CLASS));
		assertNotNull(asp.getDatatypesSchema(ECG_ARCHETYPE_CLASS));
		assertNotNull(asp.getAdlStructureSchema(ECG_ARCHETYPE_CLASS));
	}
	
	
	public void testArchetypeNotRegisteredSchemas()
	{
		assertNull(asp.getLayoutSchema(""));
		assertNull(asp.getOntologySchema(""));
		assertNull(asp.getDatatypesSchema(""));
		assertNull(asp.getAdlStructureSchema(""));
	}
	
	
}
