
<%@ include file="init.jsp"%>
<% 
/* String solrURL="http://localhost:8984/solr/new_core/select?q=emailAddress%3A%22training%22&fl=emailAddress&wt=json&indent=true"; */
String solrURL="http://localhost:8984/solr/LREE/select?q=emailAddress%3A%22training%22&fl=emailAddress&wt=json&indent=true";
String contextPath=request.getContextPath();
%>


<script>
 $(function() {
	 /* var URL_PREFIX = "http://localhost:8984/solr/new_core/select?rows=10&indent=true&fl=emailAddress,firstName,lastName&q=emailAddress:"; */
	 var URL_PREFIX = "http://localhost:8984/solr/LREE/select?rows=10&indent=true&fl=emailAddress,firstName,lastName&q=emailAddress:";
	 var URL_SUFFIX = "&wt=json";
	 $("#searchBox").autocomplete({
		 source : function(request, response) {
		 var URL = URL_PREFIX + $("#searchBox").val() + URL_SUFFIX;
		 console.log(URL);
		 $.ajax({
			 url : URL,
			 success : function(data) {
				 var docs = JSON.stringify(data.response.docs);
				 var jsonData = JSON.parse(docs);
				 response($.map(jsonData, function(value, key) {
				 return {
				 //label : value.name
				 label : value.emailAddress
				 }
				 }));
			 },
			 dataType : 'jsonp',
			 jsonp : 'json.wrf'
		 });
		 },
		 minLength : 1
	 })
 });
</script>
</head>
<body>
	<div>
		<label for="searchBox">Search: </label> <input id="searchBox"></input>
	</div>
