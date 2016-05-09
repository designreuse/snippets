<%@page import="com.liferay.portal.kernel.util.Constants"%>
<%@ include file="init.jsp"%>
<portlet:resourceURL var='myInfo' id='myInfo' />
<input class="typeahead">

    <script type="text/javascript">
    var things = new Bloodhound({
        datumTokenizer: function(datum) {
        	console.log(datum);
        	console.log("datum");
            return Bloodhound.tokenizers.whitespace(datum.emailAddress);
        },
        queryTokenizer: Bloodhound.tokenizers.whitespace,
        remote: {
            wildcard: '%QUERY',
           
            /* url: 'http://localhost:8984/solr/LREE/select?wt=json&indent=true&q=emailAddress:%QUERY', */
            url: '<%=myInfo %>&token=%QUERY',
            /* ajax: {
                dataType: 'jsonp',
                data: {
                    'wt': 'json',
                },
                jsonp: 'json.wrf'
            }, */
            transform: function(response) {
            	
                return $.map(response, function(value, key) {
                    return {
                    	emailAddress: value.emailAddress,
                        firstName: value.firstName,
                        lastName: value.lastName,
                        title: value.title
                    };
                });
            }
        }
    });

    // Instantiate Typeahead
    $('.typeahead').typeahead(null, {
        hint: true,
        highlight: true,
        display: 'emailAddress',
        source: things,
        templates: {            
            				empty: ['<div class="empty-message">','unable to find any users email that match the current query','</div>'].join('\n'),
            				suggestion: function(data){
            					fn = data.firstName || '';
            					ln = data.lastName || '';
            					eml = data.emailAddress || '';
            					title = data.title || '';

            					/* return '<p><strong>' + eml + '</strong> <br/>' + title + ' - ' + fn + ' - ' +  ln + '</p>'; */
            				      return '<p><strong>' + eml + '</strong> <br/>' + fn + ' - ' +  ln + '</p>';
            				    }
                                    }
                          		  
    });
    </script>
