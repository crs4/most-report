/**
 * Project MOST - Moving Outcomes to Standard Telemedicine Practice
 * http://most.crs4.it/
 *
 * Copyright 2014, CRS4 srl. (http://www.crs4.it/)
 * Dual licensed under the MIT or GPL Version 2 licenses.
 * See license-GPLv2.txt or license-MIT.txt
 */

package it.crs4.most.report.ehr;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;


/**
 * Utility class that provides a convenient way for getting the json schemas needed for loading an archetype on the ehrViewer.
 *
 */
public class ArchetypeSchemaProvider {
      private Context context;
      private Properties properties;
      private String schemasRootDir = null;

      /**
       * Provides default datatypes, layouts and ontology schema for specific archetypes.
       * All available archetype schema must be specified in a property file provided as input argument
       * @param context the application context
       * @param archetypesPropertyFile the path of the Archetype Property file, containing a list of key-values like <archetype_class_name>=<archetype_folder>
       * @param schemasRootDir the root dir (under assets folder) containing all archetypes schemaa (note that each subfolder is a folder for a specific archetype. e.g blood_pressure)
       */
      public ArchetypeSchemaProvider(Context context, String archetypesPropertyFile, String schemasRootDir ) {
             this.context = context;
             this.schemasRootDir = schemasRootDir;
             this.properties = new Properties();
             this.loadProperties(archetypesPropertyFile);
      }
      
      private String getSchema(String archetypeClass, String schemaType)
      {
    	  String archDir = this.properties.getProperty(archetypeClass);
    	  String filename = String.format("%s__%s.json", schemaType, archDir);
    	  String filePath = String.format("%s/%s/%s", this.schemasRootDir, archDir, filename);
    	  return WidgetProvider.parseFileToString(this.context, filePath);
      }
      
      /**
       * Get the Layout schema for the specified archetype class, or null if not available
       * @param archetypeClass the archetype class, (e.g openEHR-EHR-OBSERVATION.blood_pressure.v1)
       * @return the json schema of the default layout, or null if not available
       */
      public String getLayoutSchema(String archetypeClass)
      {
    	  return getSchema(archetypeClass, "layout");
      }
      
      /**
       * Get the ontology schema for the specified archetype class, or null if not available
       * @param archetypeClass the archetype class, (e.g openEHR-EHR-OBSERVATION.blood_pressure.v1)
       * @return the json schema of the default ontology, or null if not available
       */
      public String getOntologySchema(String archetypeClass)
      {
    	  return getSchema(archetypeClass, "ontology");
      }
      
      /**
       * Get the datatypes schema for the specified archetype class, or null if not available
       * @param archetypeClass the archetype class, (e.g openEHR-EHR-OBSERVATION.blood_pressure.v1)
       * @return the json schema containing the internal structure of datatypes used for visually representing this specific archetype, or null if not available
       */
      public String getDatatypesSchema(String archetypeClass)
      {
    	  return getSchema(archetypeClass, "datatypes");
      }
      
      /**
       * Get the adl schema for the specified archetype class, or null if not available
       * @param archetypeClass the archetype class, (e.g openEHR-EHR-OBSERVATION.blood_pressure.v1)
       * @return the json schema representing the internal structure of this specific archetype, or null if not available
       */
      public String getAdlStructureSchema(String archetypeClass)
      {
    	  return getSchema(archetypeClass, "adl_structure");
      }
      
   
      private void loadProperties(String FileName) {

             try {
                    /**
                     * getAssets() Return an AssetManager instance for your
                     * application's package. AssetManager Provides access to an
                     * application's raw asset files;
                     */
                    AssetManager assetManager = context.getAssets();
                    /**
                     * Open an asset using ACCESS_STREAMING mode. This
                     */
                    InputStream inputStream = assetManager.open(FileName);
                    /**
                     * Loads properties from the specified InputStream,
                     */
                    properties.load(inputStream);

             } catch (IOException e) {
                    // TODO Auto-generated catch block
                    Log.e("AssetsPropertyReader",e.toString());
             }
      }

}