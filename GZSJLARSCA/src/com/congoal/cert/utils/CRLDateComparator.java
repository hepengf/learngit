package com.congoal.cert.utils;

import java.security.cert.X509CRLEntry;
import java.util.Comparator;

public class CRLDateComparator implements Comparator {

	public int compare(Object o1, Object o2) {
		X509CRLEntry entry1 = (X509CRLEntry) o1;
		X509CRLEntry entry2 = (X509CRLEntry) o2;
		int compare = entry1.getRevocationDate().compareTo(
				entry2.getRevocationDate());
		if (compare == 0) {
			String sn1 = ValidateCRL.getSerialNumber(entry1);
			String sn2 = ValidateCRL.getSerialNumber(entry2);
			compare = sn1.compareTo(sn2);
		}
		return compare;
	}
	
	public static void main(String[] args) {
//		 实现方法有2种：TreeSet和List。
//
//		    第一种 TreeSet  

//		Set tSet = crl.getRevokedCertificates();
//
//		//----------加入代码开始----------
//		TreeSet ts = new TreeSet(new CRLDateComparator());
//		ts.addAll(set);
//		//----------加入代码结束----------
//
//		Iterator tIterator = ts.iterator();
		
		
//		第二种 List 
//
//		Java代码  
//		Set tSet = crl.getRevokedCertificates();   
//		  
//		//----------加入代码开始----------   
//		List l = new ArrayList();   
//		l.addAll(set);   
//		Collections.sort(l, new CRLDateComparator());   
//		//----------加入代码结束----------   
//		  
//		Iterator tIterator = l.iterator();
	}
}
