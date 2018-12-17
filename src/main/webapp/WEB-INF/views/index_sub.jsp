<!Document html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@include file="template.jsp"%>



<head>
    <title>here is index</title>
    <link rel="stylesheet" href="../resources/CSS/bootstrap.min.css" />
</head>
<body >

<form>
    <div class="justify-content-md-center">
            <div class="col-sm-10">
                <select class="form-control" id ="province" onchange="categoryChange(this)" >
    <option>시/도 선택</option>
    <option value="서울특별시 ">서울특별시</option>
    <option value="인천광역시 ">인천광역시</option>
    <option value="대전광역시 ">대전광역시</option>
    <option value="광주광역시 ">광주광역시</option>
    <option value="대구광역시 ">대구광역시</option>
    <option value="울산광역시 ">울산광역시</option>
    <option value="부산광역시 ">부산광역시</option>
    <option value="경기도 ">경기도</option>
    <option value="강원도 ">강원도</option>
    <option value="충청북도 ">충청북도</option>
    <option value="충청남도 ">충청남도</option>
    <option value="전라북도 ">전라북도</option>
    <option value="전라남도 ">전라남도</option>
    <option value="경상북도 ">경상북도</option>
    <option value="경상남도 ">경상남도</option>
    <option value="제주도 ">제주도</option>
    <script>var province = document.getElementById("province");</script>
</select>

<select class="form-control" id="city">
    <option>시군구를 선택해 주세요</option>
</select>
<script>
    var link_url = null;
    function categoryChange(e) {
    var _seoul   = ["강남구","강동구","강북구","강서구","관악구","광진구","구로구","금천구","노원구","도봉구","동대문구","동작구","마포구","서대문구","서초구","성동구","성북구","송파구","양천구","영등포구","용산구","은평구","종로구","중구","중랑구"];
    var _incheon = ["계양구","남구","남동구","동구","부평구","서구","연수구","중구","강화군","옹진군"];
    var _daejeon = ["대덕구","동구","서구","유성구","중구"];
    var _gwangju =  ["광산구","남구","동구",     "북구","서구"];
    var _daegu   =  ["남구","달서구","동구","북구","서구","수성구","중구","달성군"];
    var _ulsan   = ["남구","동구","북구","중구","울주군"];
    var _busan   = ["강서구","금정구","남구","동구","동래구","부산진구","북구","사상구","사하구","서구","수영구","연제구","영도구","중구","해운대구","기장군"];
    var _gyunggi =  ["고양시","과천시","광명시","광주시","구리시","군포시","김포시","남양주시"];
    var _gangwon =  ["강릉시","동해시","삼척시","속초시","원주시","춘천시","태백시","고성군"];
    var _chungbuk= ["제천시","청주시","충주시","괴산군","단양군","보은군","영동군","옥천군"];
    var _chungnam= ["계룡시","공주시","논산시","보령시","서산시","아산시","천안시","금산군"];
    var _jeonbuk =  ["군산시","김제시","남원시","익산시","전주시","정읍시","고창군","무주군"];
    var _jeonnam =  ["광양시","나주시","목포시","순천시","여수시","강진군","고흥군","곡성군"];
    var _kyungbuk= ["경산시","경주시","구미시","김천시","문경시","상주시","안동시","영주시"];
    var _kyungnam= ["거제시","김해시","마산시","밀양시","사천시","양산시","진주시","진해시"];
    var _jeju    = ["서귀포시","제주시","남제주군","북제주군"];
    var target = document.getElementById("city");


    if(e.value == "서울특별시 ") var d = _seoul;
    else if(e.value == "인천광역시 ") var d = _incheon ;
    else if(e.value == "대전광역시 ")var  d = _daejeon ;
    else if(e.value == "광주광역시 ")var  d = _gwangju ;
    else if(e.value == "대구광역시 ")var  d = _daegu   ;
    else if(e.value == "울산광역시 ") var d = _ulsan   ;
    else if(e.value == "경기도 ") var d = _gyunggi ;
    else if(e.value == "강원도 ") var d = _gangwon ;
    else if(e.value == "충청북도 ")var d = _chungbuk;
    else if(e.value == "충청남도 ") var d = _chungnam;
    else if(e.value == "전라북도 ") var d = _jeonbuk ;
    else if(e.value == "전라남도 ") var d = _jeonnam ;
    else if(e.value == "경상북도 ")var d = _kyungbuk;
    else if(e.value == "경상남도 ")var d = _kyungnam;
    else if(e.value == "제주도 ") var d = _jeju    ;
    else if(e.value == "부산광역시 ") var d = _busan   ;

    target.options.length = 0;
    city.current
    console.log(d.value);



    for (x in d) {
        var opt = document.createElement("option");
        opt.value = d[x];
        opt.innerHTML = d[x];
        target.appendChild(opt);
    }
        //위치
        var Location = province.options[province.selectedIndex].value + target.options[target.selectedIndex].value;

        //날짜
        var doc = new Date();
        if(doc.getMonth()<9 && doc.getTime()!=00 ){
            var date = doc.getFullYear().toString()+"0"+(doc.getMonth()+1).toString()+doc.getDate().toString();
        }
        else  var date = doc.getFullYear().toString()+(doc.getMonth()+1).toString()+doc.getDate().toString();

        if(doc.getHours()-1<10 && doc.getHours() != 0) {
            var time = "0"+(doc.getHours()-1).toString() + "30";
        }else var time = (doc.getHours()-1).toString() + "30";

        link_url = "/MineralSpring/AsLocation?location="+Location.toString()+"&time="+time.toString()+"&date="+date.toString();

    }
    function go(){
        window.location.href = link_url;
    }
</script>
                <a href="javascript:void(0);" onclick="go(); return false;"> <button class="btn btn-info"role="button"> 찾기 </button> </a>
        </div>
    </div>
</div>
</form>
</body>
</html>