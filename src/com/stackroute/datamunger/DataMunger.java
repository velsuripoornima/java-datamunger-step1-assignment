package com.stackroute.datamunger;

/*There are total 5 DataMungertest files:
 * 
 * 1)DataMungerTestTask1.java file is for testing following 3 methods
 * a)getSplitStrings()  b) getFileName()  c) getBaseQuery()
 * 
 * Once you implement the above 3 methods,run DataMungerTestTask1.java
 * 
 * 2)DataMungerTestTask2.java file is for testing following 3 methods
 * a)getFields() b) getConditionsPartQuery() c) getConditions()
 * 
 * Once you implement the above 3 methods,run DataMungerTestTask2.java
 * 
 * 3)DataMungerTestTask3.java file is for testing following 2 methods
 * a)getLogicalOperators() b) getOrderByFields()
 * 
 * Once you implement the above 2 methods,run DataMungerTestTask3.java
 * 
 * 4)DataMungerTestTask4.java file is for testing following 2 methods
 * a)getGroupByFields()  b) getAggregateFunctions()
 * 
 * Once you implement the above 2 methods,run DataMungerTestTask4.java
 * 
 * Once you implement all the methods run DataMungerTest.java.This test case consist of all
 * the test cases together.
 */

public class DataMunger {

	/*
	 * This method will split the query string based on space into an array of words
	 * and display it on console
	 */

	public String[] getSplitStrings(String queryString) {
		String result[]=queryString.toLowerCase().split(" ");
		return result;
	}

	/*
	 * Extract the name of the file from the query. File name can be found after a
	 * space after "from" clause. Note: ----- CSV file can contain a field that
	 * contains from as a part of the column name. For eg: from_date,from_hrs etc.
	 * 
	 * Please consider this while extracting the file name in this method.
	 */

	public String getFileName(String queryString) {

		int fromIndex=queryString.lastIndexOf("from ");
		fromIndex=fromIndex+5;
		String filename="";
		int toIndex= queryString.indexOf(" ",fromIndex);
		if (toIndex < 0){
			 filename=queryString.substring(fromIndex);
		}else {
			filename = queryString.substring(fromIndex,toIndex);
		}
		return filename;
	}

	/*
	 * This method is used to extract the baseQuery from the query string. BaseQuery
	 * contains from the beginning of the query till the where clause
	 * 
	 * Note: ------- 1. The query might not contain where clause but contain order
	 * by or group by clause 2. The query might not contain where, order by or group
	 * by clause 3. The query might not contain where, but can contain both group by
	 * and order by clause
	 */
	
	public String getBaseQuery(String queryString) {
		int fromIndex=queryString.lastIndexOf("from ");
		fromIndex=fromIndex+5;

		int toIndex= queryString.indexOf(" ",fromIndex);
		if (toIndex < 0){
		return queryString;
		}
		else
			{
			return queryString.substring(0,toIndex);
		}

	}

	/*
	 * This method will extract the fields to be selected from the query string. The
	 * query string can have multiple fields separated by comma. The extracted
	 * fields will be stored in a String array which is to be printed in console as
	 * well as to be returned by the method
	 * 
	 * Note: 1. The field name or value in the condition can contain keywords
	 * as a substring. For eg: from_city,job_order_no,group_no etc. 2. The field
	 * name can contain '*'
	 * 
	 */
	
	public String[] getFields(String queryString) {
		int fromindex=queryString.indexOf("Select ");
		fromindex += 8;
		int toindex=queryString.indexOf("from ");
		String fields=queryString.substring(fromindex,toindex-1);
		boolean commaseparated=fields.contains(",");
		if(commaseparated){
			String fieldelements[]=fields.split(",");
			for(int i=0;i<fieldelements.length;i++){
				System.out.println(fieldelements[i]);
			}
			return fieldelements;
		}else{
//			System.out.println(fields);
			String fieldelements[]={fields.trim()};
			return fieldelements;
		}
	}

	/*
	 * This method is used to extract the conditions part from the query string. The
	 * conditions part contains starting from where keyword till the next keyword,
	 * which is either group by or order by clause. In case of absence of both group
	 * by and order by clause, it will contain till the end of the query string.
	 * Note:  1. The field name or value in the condition can contain keywords
	 * as a substring. For eg: from_city,job_order_no,group_no etc. 2. The query
	 * might not contain where clause at all.
	 */
	
	public String getConditionsPartQuery(String queryString) {
		queryString=queryString.toLowerCase();
		int toindex=queryString.indexOf("where ");
		int havingIndex = Integer.MAX_VALUE;
		int groupIndex = Integer.MAX_VALUE;
		int orderIndex = Integer.MAX_VALUE;

		if(queryString.contains("having "))
		{
			havingIndex = queryString.indexOf("having ");
		}

		if(queryString.contains("group by "))
		{
			groupIndex = queryString.indexOf("group by ");
		}

		if(queryString.contains("order by "))
		{
			orderIndex = queryString.indexOf("order by");
		}

		int endIndex;

		if(havingIndex < groupIndex && havingIndex < orderIndex)
		{
			endIndex = havingIndex;
		}
		else if(groupIndex< orderIndex)
		{
			endIndex = groupIndex;
		}
		else
		{
			endIndex = orderIndex;
		}

		if(endIndex == Integer.MAX_VALUE)
		{
			endIndex = queryString.length();
		}


		if(toindex <0){
			return null;
		}else {
			toindex+=6;
			return queryString.substring(toindex,endIndex).trim();
		}

	}

	/*
	 * This method will extract condition(s) from the query string. The query can
	 * contain one or multiple conditions. In case of multiple conditions, the
	 * conditions will be separated by AND/OR keywords. for eg: Input: select
	 * city,winner,player_match from ipl.csv where season > 2014 and city
	 * ='Bangalore'
	 * 
	 * This method will return a string array ["season > 2014","city ='bangalore'"]
	 * and print the array
	 * 
	 * Note: ----- 1. The field name or value in the condition can contain keywords
	 * as a substring. For eg: from_city,job_order_no,group_no etc. 2. The query
	 * might not contain where clause at all.
	 */

	public String[] getConditions(String queryString) {
		queryString=queryString.toLowerCase();
		int toindex=queryString.indexOf("where ");
		int havingIndex = Integer.MAX_VALUE;
		int groupIndex = Integer.MAX_VALUE;
		int orderIndex = Integer.MAX_VALUE;

		if(queryString.contains("having "))
		{
			havingIndex = queryString.indexOf("having ");
		}

		if(queryString.contains("group by "))
		{
			groupIndex = queryString.indexOf("group by ");
		}

		if(queryString.contains("order by "))
		{
			orderIndex = queryString.indexOf("order by");
		}

		int endIndex;

		if(havingIndex < groupIndex && havingIndex < orderIndex)
		{
			endIndex = havingIndex;
		}
		else if(groupIndex< orderIndex)
		{
			endIndex = groupIndex;
		}
		else
		{
			endIndex = orderIndex;
		}

		if(endIndex == Integer.MAX_VALUE)
		{
			endIndex = queryString.length();
		}

		if(toindex <0){
			return null;
		}else {
			toindex+=6;
			String conditionpart= queryString.toLowerCase().substring(toindex,endIndex);
			String condition[]=conditionpart.split("(and )|(or )");
			for(int i=0;i< condition.length;i++){
				System.out.println(condition[i]);
				condition[i]=condition[i].trim();
			}
			return  condition;
		}

	}

	/*
	 * This method will extract logical operators(AND/OR) from the query string. The
	 * extracted logical operators will be stored in a String array which will be
	 * returned by the method and the same will be printed Note:  1. AND/OR
	 * keyword will exist in the query only if where conditions exists and it
	 * contains multiple conditions. 2. AND/OR can exist as a substring in the
	 * conditions as well. For eg: name='Alexander',color='Red' etc. Please consider
	 * these as well when extracting the logical operators.
	 * 
	 */

	public String[] getLogicalOperators(String queryString) {
		queryString = queryString.toLowerCase();
		String operator="";
		if(queryString.contains(" and ")){
			operator=operator+" and";
		}
		if(queryString.contains(" or ")){
			operator += " or";
		}
		if(queryString.contains(" and/or ")){
			operator += " and/or";
		}

		String logicalOperator[] = operator.trim().split(" ");

		return operator == "" ? null : logicalOperator;
	}

	/*
	 * This method extracts the order by fields from the query string. Note: 
	 * 1. The query string can contain more than one order by fields. 2. The query
	 * string might not contain order by clause at all. 3. The field names,condition
	 * values might contain "order" as a substring. For eg:order_number,job_order
	 * Consider this while extracting the order by fields
	 */

	public String[] getOrderByFields(String queryString) {
		queryString=queryString.toLowerCase();
		int orderIndex=queryString.indexOf(" order by ");
		if(orderIndex < 0){
			return null;
		}else{
			orderIndex += 10;
			String orderByFields=queryString.substring(orderIndex);
			String orderByFieldElements[]=orderByFields.split(",");
			return orderByFieldElements;
		}

	}

	/*
	 * This method extracts the group by fields from the query string. Note:
	 * 1. The query string can contain more than one group by fields. 2. The query
	 * string might not contain group by clause at all. 3. The field names,condition
	 * values might contain "group" as a substring. For eg: newsgroup_name
	 * 
	 * Consider this while extracting the group by fields
	 */

	public String[] getGroupByFields(String queryString) {
		queryString=queryString.toLowerCase();
		int groupIndex=queryString.indexOf(" group by ");
		if(groupIndex < 0){
			return null;
		}else{
			groupIndex += 10;
			String groupByFields=queryString.substring(groupIndex);
			String groupByFieldElements[]=groupByFields.split(",");
			return groupByFieldElements;
		}

	}

	/*
	 * This method extracts the aggregate functions from the query string. Note:
	 *  1. aggregate functions will start with "sum"/"count"/"min"/"max"/"avg"
	 * followed by "(" 2. The field names might
	 * contain"sum"/"count"/"min"/"max"/"avg" as a substring. For eg:
	 * account_number,consumed_qty,nominee_name
	 * 
	 * Consider this while extracting the aggregate functions
	 */

	public String[] getAggregateFunctions(String queryString) {
		int startIndex = queryString.indexOf("Select ");

		startIndex = startIndex+8;

		int endIndex = queryString.indexOf("from ");

		String aggregateFunctionsString = "";

		String fieldString = queryString.substring(startIndex,endIndex-1);

		boolean containsComma = fieldString.contains(",");

		if(containsComma)
		{
			String fields[] = fieldString.split(",");
			for (int i=0; i< fields.length; i++)
			{
				if(fields[i].contains("("))
				{
					aggregateFunctionsString += " " + fields[i];
				}
				System.out.println(fields[i]);
			}
		}
		else
		{
			if(fieldString.contains("("))
			{
				aggregateFunctionsString = fieldString;
			}
			else
			{
				return null;
			}
		}

		String aggregateFunctions[] = aggregateFunctionsString.trim().split(" ");
		return aggregateFunctions;
	}
}