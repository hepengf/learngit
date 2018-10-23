CertificateIssuerType = {
	HKPOSTCA : "Hongkong Post e-Cert CA 1",
    HKPOSTCATrial : "Hongkong Post Trial e-Cert CA 1",
    SZCA : "SZCA",
    HBCA : "HBCA",
    GDCA : "GDCA Guangdong Certificate Authority",
    GDCAMANAGE : "管理CA",
    Unknow : "Unknow"
};

CertificateKeyUsage = {
	Sign : "Sign",
    Encrypt : "Encrypt",
    SignAndEncrypt : "SignAndEncrypt",
    AllUsage : "AllUsage",
    Unknow : "Unknow"
};

HKPostCertType = {
	Minor : "Minor",
    Personal : "Personal",
    Encipherment : "Encipherment",
    Organisational : "Organisational",
    Server : "Server",
    Unknow : "Unknow"
};

CertificateStatus = {
	Active : "Active",
	Expired : "Expired",
	Unenforced : "Unenforced"
};

extraInfo = function (Certificate){
		


		this.Certificate = Certificate;
		this.SubjectName = Certificate.SubjectName ;
		this.IssuerName = Certificate.IssuerName;
		//这个地方必须要将证书中的ValidFromDate和ValidToDate转换为Date对象，因为直接从整数内的ValidFromDate和ValidToDate是一个特殊的Date类型
		this.ValidFromDate = new Date(Certificate.ValidFromDate+"");
		this.ValidToDate = new Date(Certificate.ValidToDate+"");
		this.SerialNumber = Certificate.SerialNumber;
		//得到整数内的Subject的email
		this.CertEmail = Certificate.getInfo(CAPICOM_INFO_SUBJECT_EMAIL_NAME);
		
		
		this.formatDate = function(date){
	        if(null == date){
	            return "";
	        }

	        //var year = 1900+date.getYear();
			var year = date.getYear();
	        var month = date.getMonth()+1;
	        if(month < 10) month = "0"+month;

	        var day = date.getDate();
	        if(day < 10) day = "0"+day;

	        var hour = date.getHours();
	        if(hour < 10) hour = "0"+hour;
	        var minute = date.getMinutes();
	        if(minute < 10) minute = "0"+minute;
	        var second = date.getSeconds();
	        if(second < 10) second = "0"+second;
	        return day+"/"+month+"/"+year;
	    };
	};
	
	
	extraInfo.prototype = {
			getEmail : function(){
				return this.email;
			},
			getCountry : function(){
				return this.country;
			},
			getCertOrganisation : function(){
				return this.certOrganisation;
			},
			getSubscriberName : function(){
				return this.subscriberName;
			},
			getSN : function(){
				return this.sn;
			},
			getStartDate : function(){
				return this.startDate;
			},
			getEndDate : function(){
				return this.endDate;
			},
			getIssuerCountry : function(){
				return this.issuerCountry;
			},
			getIssuerOrganisation : function(){
				return this.issuerOrganisation;
			},
			getIssuerCN : function(){
				return this.issuerCN;
			},
			getOrganisationEnglishName : function(){
				return this.organisationEnglishName;
			},
			getOrganisationEnglishBranchName : function(){
				return this.organisationEnglishBranchName;
			},
			getRegistrationNum : function(){
				return this.registrationNum;
			},
			getBRNum : function(){
				return this.BRNum;
			},
			getCRNum : function(){
				return this.CRNum;
			},
			getSrn : function (){
				return this.srn;
			},
			getTrustId : function(){
				return this.trustId;
			},
			//给相关属性
			espsplit : function(){
					if(this.SubjectName==""){
						alert("Certificate SubjectName is Null!");
					}
					if(this.IssuerName == ""){
						alert("Certificate IssuerName is Null!");	
					}
					if(this.ValidFromDate == null){
						alert("Certificate ValidFromDate is Null!");
					}
					if(this.ValidToDate == null){
						alert("Certificate ValidToDate is Null!");
					}
					
					var subs = this.SubjectName.split(",");
					
					var issuerNames = this.IssuerName.split(",");
					//Split Issuer Name
					for(var j = 0 ; j < issuerNames.length ; j++){
						if(issuerNames[j].indexOf("CN=") != -1){
							var cns = issuerNames[j].split("=");
							this.issuerCN = cns[1];
						}
						if(issuerNames[j].indexOf("O=") != -1){
							var os = issuerNames[j].split("=");
							this.issuerOrganisation = os[1];
						}
						if(issuerNames[j].indexOf("C=") != -1){
							var cs = issuerNames[j].split("=");
							this.issuerCountry = cs[1];
						}
					}
					var i;
					var subNames;
					var certOrg;
					var certcs;
					var emails;
					if(CertificateIssuerType.HKPOSTCA.indexOf(this.issuerCN) != -1 || CertificateIssuerType.HKPOSTCATrial.indexOf(this.issuerCN) != -1){
						var ouList = new Array();
						for(i = 0 ; i < subs.length ; i++){
							if(subs[i].indexOf("CN=")!=-1){
								subNames = subs[i].split("=");
								this.subscriberName = subNames[1];
							}
							if(subs[i].indexOf("O=")!=-1){
								certOrg = subs[i].split("=");
								this.certOrganisation = certOrg[1];
							}
							if(subs[i].indexOf("C=")!=-1){
								certcs = subs[i].split("=");
								this.country = certcs[1];
							}
							if(subs[i].indexOf("E=")!=-1){
								emails = subs[i].split("=");
								this.email = emails[1];
							}
							if(subs[i].indexOf("OU=")!=-1){
								ouList.push(subs[i]);
							}
						}
						if(ouList.length > 0){
							var srns ;
							var regOrgs;
							var branchNames;
							var regNums;
							if(ouList.length == 1){
								srns = ouList[0].split("=");
								this.srn = srns[1];
							}
							if(ouList.length == 3){
								regOrgs = ouList[0].split("=");
								this.organisationEnglishName = regOrgs[1];
								regNums = ouList[1].split("=");
								this.registrationNum = regNums[1];
								srns = ouList[2].split("=");
								this.srn = srns[1];
							}
							if(ouList.length ==4){
								branchNames = ouList[0].split("=");
								this.organisationEnglishBranchName = branchNames[1];
								regOrgs = ouList[1].split("=");
								this.organisationEnglishName = regOrgs[1];
								regNums = ouList[2].split("=");
								this.registrationNum = regNums[1];
								srns = ouList[3].split("=");
								this.srn = srns[1];
							}
						}
						if(this.registrationNum){
							this.BRNum = this.registrationNum.substring(0,16);
						}
						if(this.registrationNum){
							this.CRNum = this.registrationNum.substring(16);
						}
						if(this.ValidFromDate){
							this.startDate =this.formatDate(this.ValidFromDate);
						}
						if(this.ValidToDate){
							this.endDate = this.formatDate(this.ValidToDate);
						}
						if(this.SerialNumber){
							this.sn = this.SerialNumber;
						}
						
					}
					
					if(CertificateIssuerType.HBCA.indexOf(this.issuerCN) != -1 || CertificateIssuerType.GDCA.indexOf(this.issuerCN) != -1 || 
							CertificateIssuerType.SZCA.indexOf(this.issuerCN) != -1 || CertificateIssuerType.GDCAMANAGE.indexOf(this.issuerCN) != -1){
						for( i = 0 ; i < subs.length ; i++){
							if(subs[i].indexOf("CN=")!=-1){
								subNames = subs[i].split("=");
								this.subscriberName = subNames[1];
							}
							if(subs[i].indexOf("O=")!=-1){
								certOrg = subs[i].split("=");
								this.certOrganisation = certOrg[1];
							}
							if(subs[i].indexOf("C=")!=-1){
								certcs = subs[i].split("=");
								this.country = certcs[1];
							}
							if(CertificateIssuerType.HBCA.indexOf(this.issuerCN) != -1 ){
								if(this.CertEmail){
									this.email = this.CertEmail;
								}
							}else{
								if(subs[i].indexOf("E=")!=-1){
									emails = subs[i].split("=");
									this.email = emails[1];
								}
							}
						}
						
						if(this.ValidFromDate){
							this.startDate =this.formatDate(this.ValidFromDate);
						}
						if(this.ValidToDate){
							this.endDate = this.formatDate(this.ValidToDate);
						}
					}
				},
				
				getCNThName : function(){ 
		            if(this.issuerCN.indexOf('Hongkong Post')==-1){
		                return properties['common.cert.certname.chinese'];
		            }else{
		                var org = this.certOrganisation.toUpperCase();
		                if(org.indexOf("(SERVER)")!=-1){
		                    return properties['common.cert.servername'];
		                }
		                if(org.indexOf("(ENCIPHERMENT)")!=-1){
		                    return properties['common.cert.unitname'];
		                }
		                return properties['common.cert.certname'];
		            }
		        },
		        //显示，返回显示证书信息的HTML
				show : function(){
					var certInfoMessage;
					certInfoMessage = "<table width='100%' height='100%' id='_certinfo_table' class='tiptable-expired'>";
					certInfoMessage +="<tr><th>"+ this.getCNThName()+": </th><td>"+this.getSubscriberName()+"</td></tr>";
		            //certInfoMessage += "<tr><th> "+properties['common.cert.issuer']+": </th><td>"+this.getIssuer()+"</td></tr>\n";
		            certInfoMessage += "<tr><th> "+properties['common.cert.certtype']+": </th><td>"+this.getCertificateType()+"</td></tr>\n";
		
		            if(this.email){
		                certInfoMessage += "<tr><th> "+properties['common.certEmail']+": </th><td>"+this.getEmail()+"</td></tr>\n";
		            }
		            
		            if(this.organisationEnglishName){
		                certInfoMessage += "<tr><th> "+properties['common.cert.organisation.englishname']+": </th><td>"+this.getOrganisationEnglishName()+"</td></tr>\n";
		            }
		            
		            if(this.organisationEnglishBranchName){
		            //certInfoMessage += "<tr><th> "+properties['common.cert.organisation.englishname.branch']+": </th><td>"+organisationEnglishBranchName+"</td></tr>\n";
		            }
					
		            if(this.registrationNum){
		                certInfoMessage += "<tr><th> "+properties['common.cert.registednumber']+": </th><td>"+this.getBRNum()+"</td></tr>\n";
		                certInfoMessage += "<tr><th> "+properties['common.cert.crnumber']+": </th><td>"+this.getCRNum()+"</td></tr>\n";
		            }
		
		            if(this.srn){
		                certInfoMessage += "<tr><th> "+properties['common.cert.referencenumber']+": </th><td>"+this.getSrn()+"</td></tr>\n";
		            }
		            
		            if(this.sn){
		            	certInfoMessage += "<tr><th> "+properties['common.cert.serialnumber']+": </th><td>"+this.getSN()+"</td></tr>\n";
		            }
			        certInfoMessage += "<tr><th> "+properties['common.status']+": </th><td>"+this.checkCertificateStatus()+"</td></tr>\n";
		        		
		            certInfoMessage += "<tr><th> "+properties['common.cert.begindate']+": </th><td>"+this.getStartDate()+"</td></tr>\n";
		            certInfoMessage += "<tr><th> "+properties['common.cert.enddate']+": </th><td>"+this.getEndDate()+"</td></tr>\n";
		        	if(this.trustId){
					    certInfoMessage += "<tr><th> "+properties['common.cert.gd.trustid']+": </th><td>"+this.getTrustId()+"</td></tr>\n";
					}
		            certInfoMessage += "</table>";
		            return certInfoMessage;
				},
				//得到香港证书类型，因为香港整数区分了 未成年人、个人、机构、加密、服务器证书等。
				getHKPostCertType : function(){
					var certType = this.getCertOrganisation();
					if(certType != ""){
						if(certType.indexOf("(Personal/Minor)") != -1){
							return properties['common.ca.hkpca.personal_cert.minor'];
						}
						if(certType.indexOf("(Personal)") != -1){
							return properties['common.ca.hkpca.personal_cert'];
						}
						if(certType.indexOf("Organisational") != -1){
							return properties['common.ca.hkpca.org_cert'];
						}
						if(certType.indexOf("Encipherment") != -1){
							return properties['common.ca.hkpca.encipherment_cert'];
						}
						if(certType.indexOf("Server") != -1){
							return properties['common.cert.hk.server'];	
						}
					}
					return properties['common.ca.unknown.cert'];
					
				},
				//得到当前证书的类型
				getCertificateType : function(){
		            var certLogo = "";
		            var certType = "";
		            var issStr = this.getIssuerCN().toLowerCase(); 
		            if(issStr.indexOf("hongkong post")!=-1){
		                certLogo = '<img src="/images/hkpca.gif" style="height:20px"/>';
		                //得到HKPost证书类型
						certType = this.getHKPostCertType();
		            }else if(issStr.indexOf("szca")!=-1){
		                certLogo = '<img src="/images/szca.gif" style="height:20px"/>';
		                certType = properties['common.ca.szca.cert'];
		            }else if(issStr.indexOf("hbca")!=-1){
		                certLogo = '<img src="/images/hbca.gif" style="height:20px"/>';
		                certType = properties['common.ca.hbca.cert'];
		            }else if(issStr.indexOf("gdca guangdong certificate authority")!=-1 || issStr.indexOf("管理ca")!=-1){
		                certLogo = '<img src="/images/gdca.gif" style="height:20px"/>';
		                certType = properties['common.ca.gdca.cert'];
		            }else{
		                certType = properties['common.ca.unknown.cert'];
		            }
		            return certLogo + certType;
		        },
				//得到当前证书的类型（跟上面的方法雷同）
		        getCurrentCertType : function(){
		        	var issStr = this.getIssuerCN().toLowerCase(); 
		        	if(issStr.indexOf("hongkong post e-cert")!=-1 || issStr.indexOf("Hongkong Post Trial e-Cert")!=-1){
		                return CertificateIssuerType.HKPOSTCA;
		            }else if(issStr.indexOf("szca")!=-1){
		                return CertificateIssuerType.SZCA;
		            }else if(issStr.indexOf("hbca")!=-1){
		                return CertificateIssuerType.HBCA;
		            }else if(issStr.indexOf("gdca guangdong certificate authority")!=-1 || issStr.indexOf("管理ca")!=-1){
		                return CertificateIssuerType.GDCA;
		            }else{
		              return CertificateIssuerType.Unknow;
		            }
		        },
		        //检查证书是否过期
		        isExpired : function(){
		        	var checkDate = new Date();
		        	var validToDate = this.getEndDate();
		        	if(checkDate.getTime() > validToDate){
		        		return true;
		        	}
		        	return false;
		        	
		        },
		        //检查证书是否未生效
		        isUnenforced : function(){
		        	var checkDate = new Date();
		        	var validFromDate = this.getStartDate();
		        	if(checkDate.getTime() < validFromDate){
		        		return true;
		        	}
		        	return false;
		        },
		        //检查整数的状态
		        checkCertificateStatus : function(){
		        	if(this.isExpired()){
		        		return CertificateStatus.Expired;
		        	}else if(this.isUnenforced()){
		        		return CertificateStatus.Unenforced;
		        	}else{
		        		return CertificateStatus.Active;
		        	}
		        }
		};