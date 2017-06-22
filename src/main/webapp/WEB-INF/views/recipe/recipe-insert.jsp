<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!doctype html>

 <head>


<!-- 
							name							: value														종류
  카테고리				카테고리서브					top_category													select
						카테고리 탑					sub_category												select
						인원							reqmember													select	
						시간							time															select
						난이도							level															select
						레시피 이미지					recipe_img													img
          				레시피제목					recipe_title													text
						요리소개						summary													textarea
						테그							tag

					  재료								ingr (다중값)											txt
 (레시피 재조순서)	순서내용					step1_content   (1번 step1_~ /2번 step2_~)	textarea
		 재조이미지  순서이미지					step1_img														img

-->


  <meta charset="UTF-8">


 <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.2/css/bootstrap-select.min.css">
	
  <!-- jQuery CSS -->
    <link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/themes/smoothness/jquery-ui.css">
    <!-- bootstrap -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <!-- jQuery -->
   
    <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.2/js/bootstrap-select.min.js"></script>




<!--[if IE]><style type="text/css">.pie {behavior:url(PIE.htc);}</style><![endif]-->



<script
  src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"
  integrity="sha256-T0Vest3yCU7pafRw9r+settMBX6JkKN06dqBnpQ8d30="
  crossorigin="anonymous"></script>

<style>
li{
list-style: none;

}

</style>
<script>

function addStep(step){
	
	
	$('#steps').append(
		
		'<div id=\"steps'+step+'\" class=\"form-group\" style=\"background-color:white\">'+
		'<label  class=\"col-sm-2 control-label\">Step'+step+'</label>'+
		'<div class=\"col-sm-6\">'+
		'<textarea name=\"step'+step+'_content\" class=\"form-control \" rows=\"9\"  placeholder=\"첫단계\" style=\"background-color: lightgray\"></textarea>'+
		'</div>'+
		'<img src=\"http://recipe.ezmember.co.kr/img/pic_none3.gif\"  class=\"img-thumbnail\" width=\"150px\" height=\"100px\" name=\"step'+step+'_img\">'+
		'</div>'	
			
		);
}
function addIngr(Ingr,str){
	$('#sorts').append(
	'<div id=\"ingr'+Ingr+'\" class=\"row\" style=\"margin-bottom:5px\">'+ 
	'<div class=\"col-sm-6\"><input class=\"form-control\" id=\"ex1\" type=\"text\" name=\"ingr\" value='+str+'>'+
	'</div>'+
	'<button  type=\"button\"  class=\"btn btn-default btn-md\" onClick="btn_Drop('+Ingr+')">제거</button>'+
	'</div>'
	);
}
function btn_Drop(s){
	$('#ingr'+s).remove();
}

function stepCk(step){
	if(step<1)step=1;
	if(step>5)step=5;
}


$(function(){
	var step=1;
	var ingr=1;
	addStep(step);
	


	$('#ingrAddBtn').click(function(){
		
		str=$('#ingr_main').val();
		
		if(str==""){
			return;
		}
		$('#ingr_main').val("");
		addIngr(ingr,str);
		ingr++;
	
	});
	
	
	$('#addStepBtn').click(function(){
		step++;
		if(step<6)
		{
			addStep(step);
		}
		else{
		alert("더 이상추가 할수 없습니다");
		step=5;
		
		}
		
	});

    $('#removeStepBtn').click(function(){
    		
		if(step<2)return;
		  $('#steps'+step).remove();
		  step--;
		  
		  stepCk(step);
    	
	});


	$("#sorts").sortable({
		axis: "y",
		containment: "parent",
		update: function (event, ui) {
			var order = $(this).sortable('toArray', {
				attribute: 'data-order'
			});
			console.log(order);
		}
	});
});




</script>
	  <title>Document</title>
 </head>
 <body style="background-color:lightgray" >
	<div class="container" style="background-color:white" >
		 	<br>
		 	<form class="form-horizontal" >
		 		<div class="panel panel-default" style="background-color: white">

					<div class="panel-heading" >
					<h4>레시피등록</h4>

					</div>
				<div class="panel-body">
					<div class="form-group " style="background-color:white">
			      		<label  class="col-sm-2 control-label">레시피제목</label>
			      		<div class="col-sm-6">
			        	<input name="recipe_title" class="form-control" type="text" style="background-color: lightgray" placeholder="레시피를 입력해주세요">
			        	<div class="col-sm-4" style="position: absolute; left:560px;top:0px">
						
						<img name="recipe_img" src="http://recipe.ezmember.co.kr/img/pic_none4.gif"  class="img-thumbnail" width="200px" height="100px"></div>
			      		</div>

			      	</div>
					<div class="form-group" style="background-color:white">
			      		<label for="inputPassword" class="col-sm-2 control-label">요리소개</label>
			      		<div class="col-sm-6" >
			        	<textarea name="summary" class="form-control" rows="5"  placeholder="요리를 소개해주세요" style="background-color: lightgray"></textarea>
			      		</div>
			    	</div>
			    	<div class="form-group" style="background-color:white">
			      		<label for="inputPassword" class="col-sm-2 control-label" style="margin-right: 15px">카테고리</label>

			        	<select name="top_category" class="selectpicker " data-width="fit">
			        		<option>top_category</option>
			        		<option>s</option>
			        		<option>s</option>
			        	</select>


			        	<select name="sub_category" class="selectpicker" data-width="fit" >
			        		<option>sub_category</option>
			        		<option>s</option>
			        		<option>s</option>
			        	</select>

			    	</div>
			    	<div class="form-group" style="background-color:white">
			      		<label for="inputPassword" class="col-sm-2 control-label" style="margin-right: 15px">요리정보</label>
			      		<label>인원</label>
			        	<select id="reqmember" name="reqmember" class="selectpicker " data-width="fit">
			        		<option>인원</option>
			        		<option>s</option>
			        		<option>s</option>
			        	</select>

			      		<label  >시간</label>
			        	<select id="time" name="time" class="selectpicker" data-width="fit" >
			        		<option>시간</option>
			        		<option>s</option>
			        		<option>s</option>
			        	</select>
			      		<label>난이도</label>
			      		<select id="level" name="level"  class="selectpicker" data-width="fit" >
			        		<option>난이도</option>
			        		<option>s</option>
			        		<option>s</option>
			        	</select>

			    	</div>
			    </div>
			</div>
			<div class="panel panel-default">


				<div class="panel-body">

						<div class="form-group" style="background-color:white">
				      		<label   class="col-sm-2 control-label">재료</label>
							
							<div class="col-sm-2">
							 <a href="#" data-toggle="값을 입력해주세요" title="값을 입력해주세요">
							<input  id="ingr_main" class="form-control"  type="text">
							</a>
							
						
							</div>
							<div class="col-sm-2" style="padding-left:-0px">
							<button  id="ingrAddBtn" type="button" class="btn btn-default btn-md">추가</button>
							
							</div>
						
						</div>
						<div class="col-sm-5"></div>

						<div id="sorts" class="col-sm-4"><!-- 재료 --></div>
						
						

				 </div>
			</div>


			<div class="panel panel-default" >
					<div class="col-sm-12" style="background-color: white;margin-top:10px">

					<h4 style="marg">요리순서</h4>


					</div>
					<div class="col-sm-12">
					<p style="color: gray">요리의 맛이 좌우될 수 있는 중요한 부분은 빠짐없이 적어주세요.</p>


					<div class="col-sm-12" style="margin-left: 10px">
					<p style="color: gray">예) 10분간 익혀주세요 ▷ 10분간 약한불로 익혀주세요.</p>
					</div>
					</div>



				<div id class="panel-body">
					
				 <div id=steps> 
				 </div>

				 <div align=center>
						<button id="addStepBtn" type="button" class="btn btn-default btn-lg">추가</button>
						<button id="removeStepBtn" type="button" class="btn btn-default btn-lg">제거</button>
				</div>
				      	<br>


						<div class="form-group " style="background-color:white">
				      		<label   class="col-sm-2 control-label">요리완성사진</label>
				      		<div class="col-sm-2">
							<img src="http://recipe.ezmember.co.kr/img/pic_none3.gif"  class="img-thumbnail" width="150px" height="100px">
							</div>
									<div class="col-sm-2"  >
							<img src="http://recipe.ezmember.co.kr/img/pic_none3.gif"  class="img-thumbnail" width="150px" height="100px">
									
								</div>
									<div class="col-sm-2"  >
							<img src="http://recipe.ezmember.co.kr/img/pic_none3.gif"  class="img-thumbnail" width="150px" height="100px">
								</div>
									<div class="col-sm-2"  >
							<img src="http://recipe.ezmember.co.kr/img/pic_none3.gif"  class="img-thumbnail" width="150px" height="100px">
								</div>
				      	</div>


				 </div>
			</div>


			<div class="panel panel-default">


				<div class="panel-body">
<!--
						<div class="form-group" style="background-color:white">
				      		<label for="inputPassword" class="col-sm-2 control-label">요리팁</label>
				      		<div class="col-sm-10" >
				        	<textarea class="form-control" rows="5"  placeholder="요리를 소개해주세요" style="background-color: lightgray"></textarea>
				      		</div>
				    	</div>
				 </div>
			</div>
-->

			<div class="panel panel-default">



				<div class="panel-body">
						<div class="form-group " style="background-color:white">
				      		<label  class="col-sm-2 control-label">태그</label>
				      		<div class="col-sm-10">
				        	<input class="form-control" type="text"  name="tag" style="background-color: lightgray" placeholder="태그를 입력해주세요">
				      		</div>
				      	</div>
				      	<div class="col-sm-2"></div>
				      	<div class="cols-sm-10"><h5>주재료, 목적, 효능, 대상 등을 태그로 남겨주세요. <small>예) 돼지고기, 다이어트, 비만, 칼슘, 감기예방, 이유식, 초간단</small></h5></div>

				 </div>
			</div>



			 <div class="panel">
				<center>
				<button type="button" class="btn btn-default btn-lg">저장</button>
				<button type="submit" class="btn btn-default btn-lg">등록완료</button>
				<button type="reset" class="btn btn-default btn-lg">취소</button>
				</center>
			</div>



		</form>
		<!-- 레시피등록 완료 -->
	</div>



		<!-- https://silviomoreto.github.io/bootstrap-select/examples/ -->
 </body>
</html>