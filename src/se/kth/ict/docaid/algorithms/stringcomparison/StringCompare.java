package se.kth.ict.docaid.algorithms.stringcomparison;

public class StringCompare {

	private static float tcnt;
	 
	 private void findSubstr(String s1, int s1len, String s2, int s2len, Struct ss) {
	  int size = 1;
	  
	  ss.setO2(-1);
	  
	  for (int i = 0; i < (s1len - size); i++) {
	   for (int j = 0; j < (s2len - size); j++) {
	    int test_size = size;
	    
	    while (true) {
	     if ((test_size <= (s1len - i)) && (test_size <= (s2len - j))) {
	      if (s1.regionMatches(i, s2, j, test_size)) {
	       if (test_size > size || ss.getO2() < 0) {
	        ss.setO1(i);
	        ss.setO2(j);
	        size = test_size;
	       }
	       test_size++;
	      } else {
	       break;
	      }
	     } else {
	      break;
	     }
	    }
	   }
	  }
	  
	  if (ss.getO2() < 0) {
	   ss.setLen(0);   
	  } else {
	   ss.setLen(size);
	  }
	 }
	 
	 private void rsimil(String s1, int s1len, String s2, int s2len) {
	  
	  Struct ss = new Struct();
	  
	  if (s1len == 0 || s2len == 0) return;
	  
	  findSubstr(s1, s1len, s2, s2len, ss);
	  
	  if (ss.getLen() > 0) {
	   int delta1, delta2;
	   tcnt += ss.getLen() << 1;
	   rsimil(s1, ss.getO1(), s2, ss.getO2());
	   
	   delta1 = ss.getO1() + ss.getLen();
	   delta2 = ss.getO2() + ss.getLen();
	   
	   if (delta1 < s1len && delta2 < s2len) {
	    rsimil(s1.substring(delta1, s1len), s1len - delta1, s2.substring(delta2, s2len), s2len - delta2);
	   }
	  }
	  
	 }
	 
	 /**
	  * Compare the two strings using the Ratcliff/Obershelp pattern recognition algorithm -- see http://xlinux.nist.gov/dads/HTML/ratcliffObershelp.html
	  * @param s1
	  * @param s2
	  * @return
	  */
	 public float getComparisonRatcliff(String s1, String s2) {
	  int s1len, s2len;
	  float tlen;
	  
	  if (s1 == null || s2 == null) {
	   return 0;
	  } else if (s1.equals(s2)) {
	   return 1;
	  }
	  
	  s1 = s1.toLowerCase();
	  s2 = s2.toLowerCase();
	  
	  s1len = s1.length();
	  s2len = s2.length();
	  
	  tcnt = 0;
	  tlen = s1len + s2len;
	  
	  rsimil(s1, s1len, s2, s2len);
	  
	  return tcnt / tlen;
	 }
	 
	 
	 /**
	  * Compare the two strings using the Levenshtein distance -- see http://en.wikipedia.org/wiki/Levenshtein_distance
	  * @param s1 - the string that has the greatest number of characters
	  * @param s2 - the other string 
	  * @return
	  */
	 public static double getComparisonLevenshtein(String s1, String s2) {

			s1 = s1.trim();
			s2 = s2.trim();

			if (s1.length() < s2.length()) { // s1 should always be bigger
				String swap = s1;
				s1 = s2;
				s2 = swap;
			}
			int bigLen = s1.length();
			if (bigLen == 0) {
				return 1.0; /* both strings are zero length */
			}

			/*System.out.println(s1 + " ..... " + s2 + "  "
					+ (bigLen - computeEditDistance(s1, s2)) / (double) bigLen);*/

			return (bigLen - computeEditDistance(s1, s2)) / (double) bigLen;
		}

		public static int computeEditDistance(String s1, String s2) {
			s1 = s1.toLowerCase();
			s2 = s2.toLowerCase();

			int[] costs = new int[s2.length() + 1];
			for (int i = 0; i <= s1.length(); i++) {
				int lastValue = i;
				for (int j = 0; j <= s2.length(); j++) {
					if (i == 0)
						costs[j] = j;
					else {
						if (j > 0) {
							int newValue = costs[j - 1];
							if (s1.charAt(i - 1) != s2.charAt(j - 1))
								newValue = Math.min(Math.min(newValue, lastValue),
										costs[j]) + 1;
							costs[j - 1] = lastValue;
							lastValue = newValue;
						}
					}
				}
				if (i > 0)
					costs[s2.length()] = lastValue;
			}
			return costs[s2.length()];
		}

	 
	 class Struct {
	  Struct() {};
	  
	  int o1, o2, len;

	  public int getLen() {
	   return len;
	  }

	  public void setLen(int len) {
	   this.len = len;
	  }

	  public int getO1() {
	   return o1;
	  }

	  public void setO1(int o1) {
	   this.o1 = o1;
	  }

	  public int getO2() {
	   return o2;
	  }

	  public void setO2(int o2) {
	   this.o2 = o2;
	  }  
	 }
	
}
