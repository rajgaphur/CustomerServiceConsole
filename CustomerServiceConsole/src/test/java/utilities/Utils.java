package utilities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Clob;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

//import io.restassured.path.xml.XmlPath;

public final class Utils {
	
	public static String addTwoStrings(String s1, String s2) {
		return String.valueOf(Integer.parseInt(s1)+Integer.parseInt(s2));
	}
	
	public static List<String> alphabetized(List<String> origList) {
		Map<String,String> tempOrderedList = new TreeMap<String,String>();
		List<String> resultList = new ArrayList<String>();
		
		for(String origValue : origList) {
			tempOrderedList.put(origValue.toUpperCase(),origValue);
		}
		
		for(String orderedValue : tempOrderedList.keySet()) {
			//System.out.println("alphabetized:  orderedByToUpper=["+ orderedValue +"]; value=["+ tempOrderedList.get(orderedValue) +"]");
			resultList.add(tempOrderedList.get(orderedValue));
		}
		
		return resultList;
	}
	
	
	public static String capitalizeWord(String word) {
		if(word == null) return null;
		if(word.length() <= 1) return word.toUpperCase();
		return word.substring(0,1).toUpperCase() + word.substring(1);
	}
	
	public static String clobToString(Clob clob) {
		StringBuilder resultBuilder = new StringBuilder();
		
		try {
			BufferedReader br = new BufferedReader(clob.getCharacterStream());
			int b;
			
			while((b = br.read()) != -1) {
				resultBuilder.append((char)b);
			}
			
			br.close();
			
			// Alternate
			//InputStream in = clob.getAsciiStream();
			//Reader read = new InputStreamReader(in);
			//StringWriter w = new StringWriter();
			//int c = -1;
			
			//while ((c = read.read()) != -1) {
			//	w.write(c);
			//}
			
			//w.flush();
		} catch(Exception e) {
		}
		
		return resultBuilder.toString();
	}
	
	public static String convertBinaryToDecimal(String str) {
		try {
			return Integer.toString(Integer.parseInt(str,2));
		} catch(NumberFormatException e) {
			return str;
		}
	}
	
	public static String convertDecimalToBinary(String str) {
		try {
			String result = Integer.toBinaryString(Integer.parseInt(str));
			StringUtils.leftPad(result,8,'0');
			return result;
		} catch(NumberFormatException e) {
			return str;
		}
	}
	
	public static String convertDecimalToBinary(String str, Integer length) {
		try {
			StringBuilder result = new StringBuilder("");
			String shortResult = Integer.toBinaryString(Integer.parseInt(str));
			
			for(int x = 0; x < (length - shortResult.length()); x++) {
				result.append("0");
			}
			
			result.append(shortResult);
			return result.toString();
		} catch(NumberFormatException e) {
			return str;
		}
	}
	
	public static String convertDecimalToHexidecimal(String str) {
		try {
			return Integer.toHexString(Integer.parseInt(str));
		} catch(NumberFormatException e) {
			return str;
		}
	}
	
	public static String convertDecimalToHexidecimal(String str, Integer length) {
		try {
			StringBuilder result = new StringBuilder("");
			String shortResult = Integer.toHexString(Integer.parseInt(str));
			
			for(int x = 0; x < (length - shortResult.length()); x++) {
				result.append("0");
			}
			
			result.append(shortResult);
			return result.toString();
		} catch(NumberFormatException e) {
			return str;
		}
	}
	
	public static String convertDosToUnix(String str) {
		//return str.replaceAll("\\\r\\\n","\n");  // tried:  1:1, 4:1, 4:2, 3:1
		return str.replaceAll("\\\\r\\\\n","\n");
	}
	
	public static List<String> convertSetToList(Set<String> inputSet) {
		List<String> resultList = new ArrayList<String>();
		
		for(String setEntry : inputSet) {
			resultList.add(setEntry);
		}
		
		return resultList;
	}
	
	public static Document convertStringToXMLDocument(String xmlString) {
		
		DocumentBuilderFactory dfc = DocumentBuilderFactory.newInstance();
		
		DocumentBuilder docBuilder = null;
		Document document = null;
		try {
			
			docBuilder = dfc.newDocumentBuilder();
			document = docBuilder.parse(new InputSource(new StringReader(xmlString)));
			return document;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return document;
	}
	
	public static List<String> convertStreamToStrList(InputStream is) {

		if (is != null) {
			
			List<String> strLines = new ArrayList<String>();
			try {
				BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				String str = "";
				while((str = reader.readLine()) != null) {
					strLines.add(str);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return strLines;
		} else {
			return null;
		}
	}
	
	public static double distanceBetweenLatLong(String LatA, String LongA, String LatB, String LongB) {
		final int R = 6371; // Radius of the earth

		double latDistance = Math.toRadians(Double.parseDouble(LatA) - Double.parseDouble(LatB));
		double lonDistance = Math.toRadians(Double.parseDouble(LongA) - Double.parseDouble(LongB));
		double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
			+ Math.cos(Math.toRadians(Double.parseDouble(LatA))) * Math.cos(Math.toRadians(Double.parseDouble(LatB)))
			* Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		
		double distance = R * c * 1000; // convert to meters
		
		return distance;
	}
	
	public static String emptyStringToNull(String str) {
		return ((str != null) && (str.isEmpty())) ? null : str;
	}
	
	public static Boolean equal(Object obj, Object compareToObj) {
		return (obj != null) ? obj.equals(compareToObj) : (compareToObj == null);
	}
	
	public static Boolean equalNotNull(Object obj, Object compareToObj) {
		return (obj != null) ? obj.equals(compareToObj) : false;
	}
	
	
	public static HashMap<String,String> fieldsAsMap(String[] strCharArr) {
		
		HashMap<String,String> splCharAddress = new HashMap<String,String>(); 
		for (String string : strCharArr) {
			String[] tempStr = string.split("=");
			String key = "";
			String value = "";
			key = tempStr[0];							
			if(tempStr.length>1&&tempStr[1]!=null) {
				value = tempStr[1];
			}							
			splCharAddress.put(key, value);
		}
		return splCharAddress;
	}
	
		
	public static String formatFileTime (String fileTime) {   
    	String rtnFileTime = new String();
    	
    	if(!StringUtils.isBlank(fileTime)) {
    		rtnFileTime = StringUtils.replace(fileTime, ":", "");
    		rtnFileTime = StringUtils.replace(rtnFileTime, "-", "");
    		rtnFileTime = StringUtils.replace(rtnFileTime, " ", "_");
    	}
    	
    	return rtnFileTime;
	}
	
	public static void getFileContents(String filePath) {
		
		try {
			Path pathObj = Paths.get(filePath);
			
			for(String aLine : Files.readAllLines(pathObj,StandardCharsets.UTF_8)) {
				System.out.println(aLine);
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	
	public static List<String> getClassesForPackage(Package pkg) {
	    String pkgName = pkg.getName();
	    List<String> classes = new ArrayList<String>();
	    File directory = null;
	    String relPath = pkgName.replace('.','/');
	    System.out.println("ClassDiscovery: Package: "+ pkgName +" becomes Path:"+ relPath);
	    URL resource = ClassLoader.getSystemClassLoader().getResource(relPath);
	    System.out.println("ClassDiscovery: Resource = "+ resource);
	    
	    if(resource == null) {
	        throw new RuntimeException("No resource for "+ relPath);
	    }
	    
	    String fullPath = resource.getFile();
	    System.out.println("ClassDiscovery: FullPath = "+ fullPath);

	    try {
	        directory = new File(resource.toURI());
	    } catch(URISyntaxException e) {
	        throw new RuntimeException(pkgName +" ("+ resource +") does not appear to be a valid URL / URI.",e);
	    } catch(IllegalArgumentException e) {
	        directory = null;
	    }
	    
	    //System.out.println("ClassDiscovery: Directory = "+ directory);

	    // Try directory first
	    if((directory != null) && directory.exists()) {
	        classes = collectClassListInAPackageDirectory(directory,pkgName);
	    }
	    else {
	    	// If directory doesn't work, then try jar file
	    	JarFile jarFile = null;
	    	
	        try {
	            String jarPath = fullPath.replaceFirst("[.]jar[!].*",".jar").replaceFirst("file:","");
	            jarFile = new JarFile(jarPath);         
	            Enumeration<JarEntry> entries = jarFile.entries();
	            
	            while(entries.hasMoreElements()) {
	                JarEntry entry = entries.nextElement();
	                String entryName = entry.getName();
	                
	                if(entryName.endsWith(".class")) {
	                	if(entryName.startsWith(relPath) && (entryName.length() > (relPath.length() +"/".length()))) {
		                    System.out.println("ClassDiscovery: JarEntry: "+ entryName);
		                    String className = entryName.replace('/','.').replace('\\','.').replace(".class","");
		                    System.out.println("ClassDiscovery: className = "+ className);
		                    
		                    try {
		                        classes.add(Class.forName(className).getName());
		                    } catch(ClassNotFoundException e) {
		                        throw new RuntimeException("ClassNotFoundException loading "+ className);
		                    }
		                }
	                }
	            }
	        } catch(IOException e) {
	            throw new RuntimeException(pkgName +" ("+ directory +") does not appear to be a valid package",e);
	        } finally {
	        	try {
	        		jarFile.close();
	        	} catch(IOException e) {
	        		e.printStackTrace();
	        	}
	        }
	    }
	    
	    return classes;
	}
	
	public static String getPidValue(String line) {
		
		String pid = "";
		String[] values = line.split(" ");
		pid = values[1];
		return pid;
	}
	
	/*
	public static String getIfExistsInFind(XmlPath soapXml, String name) {
		if(soapXml != null) {
			return soapXml.getString("**.find {it.name() == '"+ name +"'}");
		}
		
		return null;
	}*/
	
	public static Boolean hasValue(String str) {
		return (str != null) ? (! str.isEmpty()) : false;
	}
	

	public static Boolean hasSplChar(String str) {	
		//Special Characters ` ! * _ @ \ [ ] & 
		if(str.contains("`")||str.contains("*")||str.contains("!")||str.contains("_")||str.contains("@")||str.contains("\\")||str.contains("[")||str.contains("]")||str.contains("&")) {
			return true;
		}
		
		return false;
	}
	
	public static String nullToEmptyString(Object obj) {
		return (obj == null) ? "" : String.valueOf(obj).equalsIgnoreCase("null") ? "" : String.valueOf(obj);
	}
	
	public static Integer numberEntriesInCommaDelimitedString(String str) {
		return (str != null) ? (! str.trim().isEmpty()) ? str.split(",").length : 0 : 0;
	}
	
	public static String padDecimal(String str, Integer decimalPlaces) {
		String result = str;
		
		if((result != null) && (decimalPlaces != null)) {
			result += (result.indexOf(".") < 0) ? (result.isEmpty()) ? "0." : "." : "";
			int decimalIndex = result.indexOf(".");
			System.out.println("padDecimal:  result=["+ result +"]; decimalIndex=["+ decimalIndex +"]");
			
			for(int x = ((result.length() - decimalIndex) - 1); x < decimalPlaces; x++) {
				result += "0";
			}
		}
		return result;
	}
	
	public static BigDecimal parseBigDecimal(String str) {
		return parseBigDecimal(str,new BigDecimal(0));
	}
	
	public static BigDecimal parseBigDecimal(String str, BigDecimal defaultValue) {
		BigDecimal result = defaultValue;
		
		if(str != null) {
			try {
				result = new BigDecimal(str);
			} catch(NumberFormatException e) {
				// Invalid integer
			}
		}
		
		return result;
	}
	
	public static Boolean parseBoolean(String str) {
		if(str != null) {
			if(str.equals("1")
					|| str.equalsIgnoreCase("true")
					|| str.equalsIgnoreCase("t")
					|| str.equalsIgnoreCase("y")
					|| str.equalsIgnoreCase("yes")
					|| str.equalsIgnoreCase("on")
					|| str.equalsIgnoreCase("enable")
					|| str.equalsIgnoreCase("enabled")) {
				return true;
			}
		}
		
		return false;
	}
	
	public static Boolean parseBoolean(int i) {
		if(i == 1) {
			return true;
		}
		
		return false;
	}
	
	public static Integer parseInteger(String str) {
		return parseInteger(str,0);
	}
	
	public static Integer parseInteger(String str, Integer defaultValue) {
		Integer result = defaultValue;
		
		if(str != null) {
			try {
				result = Integer.parseInt(str);
			} catch(NumberFormatException e) {
				// Invalid integer
			}
		}
		
		return result;
	}
	
	public static String parseNullStrings(String str) {
		if(str != null) {
			if(str.trim().equals("")
					|| str.equalsIgnoreCase("null")) {
				return null;
			}
			else{
				return str;
			}
		}
		
		return null;
	}
	
	public static List<URL> parseUrlList(String str) {
		List<URL> urls = new ArrayList<URL>();
		
		if(hasValue(str)) {
			
			for(String strEntry : str.split(",")) {
				
				try {
					URL url = new URL(strEntry);
					urls.add(url);
				} catch(MalformedURLException e) {
					e.printStackTrace();
				}
				
			}
		}
		
		return urls;
	}
	
	public static Boolean pause(int millis) {
		return pause((long)millis);
	}
	
	public static Boolean pause(Long millis) {
		// Return true if successful; Otherwise false.
		try {
			Thread.sleep(millis);
		} catch(InterruptedException e) {
			Thread.currentThread().interrupt();
			return false;
		} catch(IllegalArgumentException e) {
			return false;
		}
		
		return true;
	}
	
	// Produces an integer number as a String containing a specified number of digits with no leading zeros
	public static String randomDecimal(int numDigits) {
		String result = String.valueOf(ThreadLocalRandom.current().nextInt(1,10));
		
		for(int x = 1; x < numDigits; x++) {
			result += String.valueOf(ThreadLocalRandom.current().nextInt(0,10));
		}
		
		return result;
	}
	
	// Reads entire file in as one String
	public static String readFileAsString(String filePath) {
		return readFileAsString(filePath,"");
	}
	
	// Reads entire file in as one String with extra text on right end of each line
	public static String readFileAsString(String filePath, String lineEnding) {
		StringBuilder resultBuilder = new StringBuilder();
		
		try {
			Path pathObj = Paths.get(filePath);
			
			for(String aLine : Files.readAllLines(pathObj,StandardCharsets.UTF_8)) {
				resultBuilder.append(aLine + lineEnding);
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		return resultBuilder.toString();
	}
	
	public static String[] readStaleMacroNeighbourEntriesFromCSV(String valueList) {			
		BufferedReader staleMacroReader = null;
	        String line = "";
	        String[] entry = null;
	        try {
	        	
	        	String filePath = new File("env/TestData/collections/").getAbsolutePath();
	        	System.out.println("getAbsolutePath :"+filePath);
	        	staleMacroReader = new BufferedReader(new FileReader(filePath));
	        	
	            while ((line = staleMacroReader.readLine()) != null) {
	            	if(line.contains(valueList)) {
	            		entry = line.split(",");	         
	            	}
	            }
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            if (staleMacroReader != null) {
	                try {
	                	staleMacroReader.close();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	        }
			return entry;
	}
	public static java.util.Properties readPropFile(String fileName) throws IOException {
	      FileReader fiInStream = null;
	      java.util.Properties properties = null;
	      try {
	    	  fiInStream = new FileReader(fileName);
	    	  properties = new java.util.Properties();
	    	  properties.load(fiInStream);
	      } catch(FileNotFoundException e) {
	         e.printStackTrace();
	      } catch(IOException ioe) {
	         ioe.printStackTrace();
	      } finally {
	    	  fiInStream.close();
	      }
	      return properties;
	   }
	
	//removes any in between and trailing whitespaces from the string : by akanksha
	public static String removeAllSpaces(String str) {
		if(str != null) {
			StringBuilder result = new StringBuilder();
			String trimmedStr = str.trim();
			
			for(int index = 0; index < trimmedStr.length(); index++) {
				char indexedChar = trimmedStr.charAt(index);
				
				if(indexedChar != ' ') {
					result.append(indexedChar);
				}
			}
			
			return result.toString();
		}
		
		return str;
	}
	
	// Removes leading and trailing zeros and whitespace from a String
	public static String removeLeadingTrailingZeros(String str) {
		if(str != null) {
			StringBuilder result = new StringBuilder();
			String trimmedStr = str.trim();
			
			for(int index = 0; index < trimmedStr.length(); index++) {
				char indexedChar = trimmedStr.charAt(index);
				
				if(indexedChar != '0') {
					result.append(indexedChar);
				}
			}
			
			return result.toString();
		}
		
		return str;
	}
	
	// Removes  trailing zeros after decimal from a String
	public static String removeTrailingZerosAfterDecimal(String str) {
		if(str != null) {
			StringBuilder result = new StringBuilder();
			String trimmedStr = str.trim();
			int decimal_index =0;
			for(int index = 0; index < trimmedStr.length(); index++) {
				char indexedChar = trimmedStr.charAt(index);
				if (indexedChar == '.' ){
					decimal_index = index;
				}
				if(!((indexedChar == '0') && (index > decimal_index) && ((index + 1) == trimmedStr.length()))){
					result.append(indexedChar);
				}
			}
			
			return result.toString();
		}
		
		return str;
		/*DecimalFormat df=new DecimalFormat("#.#");
	    String s=df.format(Double.parseDouble(str));
		System.out.println(s);
	    return s;*/
	}
	
	public static Boolean replaceLineInFile(String filePath, String line, String uniqueIdentifier) {
		File fileObj = new File(filePath);
		
		if(fileObj.exists()) {
			List<String> newFileLines = new ArrayList<String>();
			
			try {
				Path pathObj = Paths.get(filePath);
				
				for(String aLine : Files.readAllLines(pathObj,StandardCharsets.UTF_8)) {
					
					if(aLine.contains(uniqueIdentifier)) {
						newFileLines.add(line);
					} else {
						newFileLines.add(aLine);
					}
				}
			} catch(IOException e) {
				e.printStackTrace();
			}
			
			return writeToFile(filePath,newFileLines,false);
		}
		
		return false;
	}
	
	public static String replaceSplCharacters(String str) {
		String splCharacters = "[`*!_@]";
		str = str.replaceAll(splCharacters, " ");
		str = str.replace("\\", " ");
		str = str.replace("[", "(");
		str = str.replace("]", ")");
		str = str.replace("&", "+");
		return str;
	}
	
	
	public static String changeDelimiterInCsv(String str)
	{
		return str.replaceAll(",", "|");
	}
	public static String resizedLatitude(String latitude, int numOfDigit) {
		String[] lat = latitude.split("[.]");
		String resizedLat = "";

		resizedLat = lat[0]+"."+lat[1].substring(0, numOfDigit);
		
		if(resizedLat.endsWith("0")) {
			resizedLat = resizedLatitude(resizedLat, numOfDigit-1);
		}


		return resizedLat;
	}
	
	public static String resizedLongitude(String longitude, int numOfDigit) {
		String[] lng = longitude.split("[.]");
		String resizedLong = "";

		resizedLong = lng[0]+"."+lng[1].substring(0, numOfDigit);
		if(resizedLong.endsWith("0")) {
			resizedLong = resizedLongitude(resizedLong, numOfDigit-1);
		}
		
		return resizedLong;
	}
	
	public static String setValueWithoutDecimals(String latitude) {
		String value = "";
		
		try {
			Double valueAsDouble = Double.valueOf(latitude) * 1000000;
			value = ((Integer)valueAsDouble.intValue()).toString();
		} catch(NumberFormatException e) {
		}
		
		//this.replaceParameterValue("LatitudeWithoutDecimals",value);
		return value;
	}
	
	public static Boolean similar(String str, String compareToStr) {
		return (str != null) ? str.equalsIgnoreCase(compareToStr) : (compareToStr == null);
	}
	
	public static Boolean similarNotNull(String str, String compareToStr) {
		return (str != null) ? str.equalsIgnoreCase(compareToStr) : false;
	}
	
	public static String subtractTwoStrings(String s1, String s2) {
		return String.valueOf(Integer.parseInt(s1)-Integer.parseInt(s2));
	}
	
	public static String timestamp() {
		return timestamp("yyyyMMdd_HHmmss");  // yyyyMMdd_HHmmss
	}
	
	public static String timestamp(String dateFormat) {
		//return new SimpleDateFormat(dateFormat).format(new Date());
		return timestamp(new SimpleDateFormat(dateFormat));
	}
	
	public static String timestamp(SimpleDateFormat dateFormat) {
		return dateFormat.format(new Date());
	}
	
	public static String toDecimalString(String str) {
		if((str != null) && (! str.isEmpty())) {
			if(str.startsWith("0x")) {  // hexidecimal
				return Integer.toString(Integer.parseInt(str.substring(2),16));
			} else {
				return Integer.toString(Integer.parseInt(str));
			}
		}
		return str;
	}
	
	public static String toEncoded(String str) {
		try {
			return URLEncoder.encode(str,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/*
	public static io.restassured.path.json.JsonPath toJson(String str) {
		return (str != null) ? new io.restassured.path.json.JsonPath(str) : null;
	}
	
	public static List<String> toList(String str, String delimiter) {
		if((str != null) && (delimiter != null)) {
			return Arrays.asList(str.split(delimiter));
		}
		
		return null;
	}
	
	public static io.restassured.path.xml.XmlPath toXml(String str) {
		return (str != null) ? new io.restassured.path.xml.XmlPath(str) : null;
	}
	*/
	
	public static String truncateDecimal(String str, Integer decimalPlaces) {
		if((str != null) && (decimalPlaces != null) && (str.indexOf(".") >= 0)) {
			int decimalIndex = str.indexOf(".") + decimalPlaces + 1;
			
			if(decimalIndex > str.length()) {
				decimalIndex = str.length();
			}
			return str.substring(0,decimalIndex);
		}
		return str;
	}
	
	public static int waitForMins(String comment) {
		String[] waitComments = comment.split(" ");
		System.out.println("Waiting for " +waitComments[4]+ " Minutes. ");
		int waitInMinutes = Integer.parseInt(waitComments[4]);

		return waitInMinutes*60000;
	}
	
	public static Boolean writeToFile(String filePath, List<String> lines) {
		return writeToFile(filePath,lines,true);
	}
	
	public static Boolean writeToFile(String filePath, List<String> lines, boolean append) {
		File fileObj = new File(filePath);
		
		// Setup Folder
		if((fileObj.getParentFile() != null) && (! fileObj.getParentFile().exists())) {
			fileObj.getParentFile().mkdirs();
		}
		
		try(FileWriter fw = new FileWriter(fileObj,append);
			    BufferedWriter bw = new BufferedWriter(fw);
			    PrintWriter out = new PrintWriter(bw))
		{
			for(String line : lines) {
				out.println(line);
			}
			
			out.flush();
		} catch(IOException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	//Array to csv
    public static String toCsv(List<String> inputList) {
        StringBuilder result = new StringBuilder();
        
        for(String inputEntry : inputList) {
              if(result.length() > 0) {
                    result.append(",");
              }
              
              result.append(inputEntry);
        }
        
        return result.toString();
  }

	
	public static Boolean deleteFilefromlocal(String filepath)
	{
		 Boolean isdeleted = false;
		try
        { 
            isdeleted = Files.deleteIfExists(Paths.get(filepath)); 
        } 
        catch(NoSuchFileException e) 
        { 
            System.out.println("No such file/directory exists"); 
        } 
        catch(DirectoryNotEmptyException e) 
        { 
            System.out.println("Directory is not empty."); 
        } 
        catch(IOException e) 
        { 
            System.out.println("Invalid permissions."); 
        } 
          
        System.out.println("Deletion of the file successful."); 
        return isdeleted;
	}
	
	
	private static List<String> collectClassListInAPackageDirectory(File directory, String pkgName) {
		List<String> classes = new ArrayList<String>();
		//System.out.println("ClassDiscovery: Directory = "+ directory);

	    if((directory != null) && directory.exists()) {
	        // Get the list of the files contained in the directory
	    	File[] files = directory.listFiles();
	    	List<File> directories = new ArrayList<File>();
	    	
	    	for(int i=0; i < files.length; i++) {
	    		
	    		if(files[i].isFile()) {
	    			String fileName = files[i].getName();
	    			
	    			if(fileName.endsWith(".class")) {
	    				// removes the .class extension
		    			String className = pkgName +"."+ fileName.substring(0,fileName.length() - 6);
		    			//System.out.println("ClassDiscovery: className (dir) = "+ className);
		                
		                try {
		                    classes.add(Class.forName(className).getName());
		                } catch(ClassNotFoundException e) {
		                    throw new RuntimeException("ClassNotFoundException loading "+ className);
		                //} catch(ExceptionInInitializerError e) {
		                //	e.printStackTrace();
		                }
	    			}
	    		} else {  // directory
	    			directories.add(files[i]);
	    		}
	    	}
	    	
	    	// Add sub directories
	    	for(File dir : directories) {
	    		classes.addAll(collectClassListInAPackageDirectory(dir,pkgName +"."+ dir.getName()));
	    	}
	    }
	    
	    return classes;
	}
}
