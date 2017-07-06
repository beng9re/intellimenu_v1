package com.sist.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.sist.recipe.CatSubDAO;
import com.sist.recipe.RecipeDAO;
import com.sist.recipe.RecipeInsertDAO;
import com.sist.recipe.RecipeService;
import com.sist.recipe.RecipeUpdateDAO;
import com.sist.recipe.RecipeInsertService;
import com.sist.util.FileManager;
import com.sist.util.PagingManager;
import com.sist.util.StringManager;
import com.sist.vo.CatSubVO;
import com.sist.vo.CatTopVO;
import com.sist.vo.IngrRecipeVO;
import com.sist.vo.IngredientVO;
import com.sist.vo.RecipeVO;
import com.sist.vo.RecipeContentVO;
import com.sist.vo.RecipeTagVO;

@Controller
public class RecipeController {
	
	@Autowired
	private CatSubDAO catSubDAO;	
	@Autowired
	private RecipeDAO recipeDAO;
	@Autowired
	private RecipeUpdateDAO recipeUpdateDAO;
	
	
	@Autowired
	private RecipeInsertService recipeInsertService;
	@Autowired
	private RecipeService recipeService;
	
	@RequestMapping("/recipe/recipe_insert")
	 public String recipe_insert(Model model){
	
		List<CatTopVO>list =catSubDAO.selectTopList();
		model.addAttribute("toplist", list);
		
		return "/recipe/recipe_insert";
	 }
	
	
	@RequestMapping("recipe/recipe_insertok")
	public String recipe_insertok(RecipeVO recipe,
								String tags,
								MultipartFile mainFile){	
		
		recipeInsertService.recipeInsert(recipe, tags, mainFile);
		
		
		
		return "redirect:/recipe/recipe_insert";
	}
	@RequestMapping("recipe/recipe_update")
	public String recipe_update(Model model){
		int id=66896;
		RecipeVO vo=recipeUpdateDAO.selectRecipe(id);
		int step=recipeUpdateDAO.selectStepCount(id);
		List<CatTopVO>list =catSubDAO.selectTopList();
		CatSubVO cate=recipeUpdateDAO.selectCatsub(vo.getCat_sub_id());
		List<IngrRecipeVO> ingr=recipeUpdateDAO.selectIngRecipe(id);
		int ingrSize =ingr.size();
		
		
		model.addAttribute("ingrSize",ingrSize);
		model.addAttribute("ingrlist",ingr);
		model.addAttribute("top",cate.getCat_top_id());
		model.addAttribute("sub",cate.getId());
		model.addAttribute("toplist", list);
		model.addAttribute("rvo",vo);
		model.addAttribute("step",step);
		
		
		
		return "/recipe/recipe_update";
	}
		
	
	
	
	@RequestMapping("recipe/recipe_main")
	public String recipeList(Model model){
		
		List<CatSubVO> subList= catSubDAO.selectList(1);//종류별 리스트 가져오기
		model.addAttribute("subList", subList);

		String[] tagNameArr={"표고버섯", "딸기", "아이스크림"};
		List<RecipeVO> recipeList=new ArrayList<RecipeVO>();		
		List<RecipeVO> recipeList1=new ArrayList<RecipeVO>();		
		List<RecipeVO> recipeList2=new ArrayList<RecipeVO>();		
		List<RecipeVO> recipeList3=new ArrayList<RecipeVO>();		
		
		for (String tagName : tagNameArr) {
			List<RecipeTagVO> tagList=recipeDAO.recipeTagSelectList3ByName(tagName);
			for (RecipeTagVO recipeTag : tagList) {
				RecipeVO recipe=recipeDAO.recipeDetail(recipeTag.getRecipe_id());
				recipe.setImgAuto();
				
				recipeList.add(recipe);
			}
		}
		
		int count=3;
		for (int i = 0; i < recipeList.size(); i++) {
			if (i<3) {
				recipeList1.add(recipeList.get(i));				
			}else if (i<6) {
				recipeList2.add(recipeList.get(i));				
			}else{
				recipeList3.add(recipeList.get(i));				
			}
		}
		
		model.addAttribute("recipeList1", recipeList1);
		model.addAttribute("recipeList2", recipeList2);
		model.addAttribute("recipeList3", recipeList3);
		
		
		return "recipe/recipe_main";
	}
	
	
	
	/**
	 * @param page
	 * @param id
	 * @param name
	 * @param model
	 * @return
	 * 
	 * 레시피 홈에서 상황별, 종류별 카테고리를 선택했을때 
	 * cat_sub_id를 통해서 해당 id를 가진 recipe목록을 가져온다.
	 */
	@RequestMapping("recipe/recipe_sublist")
	public String recipeSubList(PagingManager page, int cat_sub_id, String name, Model model){
		page.setRowSize(9);
		Map<String, Integer> pageCal = page.calcPage(550);//block계산 필요하지 않아 아무수나 우선 넣었다.
		
		//mybatis mappter에 사용할 map
		Map map=new HashMap();
		map.put("cat_sub_id", cat_sub_id);		
		map.put("start", pageCal.get("start"));
		map.put("end", pageCal.get("end"));
		
		List<RecipeVO> list=recipeDAO.catSubRecipeListData(map);
		for (RecipeVO vo : list) {
			//사용자가 올린 이미지가 아니라 웹에서 가져온 이미지면 oriname을 사용한다.
			vo.setImgAuto();
			
		}
		
		//totalpage 구하기
		int totalpage=recipeDAO.catSubRecipeListTotalPage(cat_sub_id);
		
		model.addAttribute("list", list);		
		model.addAttribute("page", page.getPage());
		model.addAttribute("totalpage", totalpage);
		model.addAttribute("name", name);
		model.addAttribute("cat_sub_id", cat_sub_id);
		
		return "recipe/recipe_sublist";
	}
	
	@RequestMapping("recipe/recipe_detail")
	public String recipeDetail(int id, Model model){
		
		/*RecipeVO recipe=recipeDAO.recipeDetail(id);
		
		//사용자가 올린 이미지가 아니라 웹에서 가져온 이미지면 oriname을 사용한다.
		recipe.setImgAuto();
		
		
		//조리순서 가져오기
		List<RecipeContentVO> contentList=recipeDAO.recipeDetailContent(id);
		System.out.println("contentlistsize는 "+contentList.size());
		for (RecipeContentVO vo : contentList) {
			//System.out.println(vo.getImg_ori());
			
			//사용자가 올린 이미지가 아니라 웹에서 가져온 이미지면 oriname을 사용한다.
			if (vo.getImg_new().equals("imgfromweb")) {
				vo.setImg(vo.getImg_ori());
			}else{
				vo.setImg(vo.getImg_new());				
			}
			vo.setImgAuto();
			
		}
		
		//ingr_recipe테이블과 ingredient테이블 조인
		List<IngredientVO> ingrList=recipeDAO.IngrRecipeJoin(id);
		
		List<RecipeTagVO> tagList=recipeDAO.recipeTagSelectListByRecipeId(id);*/
		
		//위의 내용을 service로 뺐다.
		RecipeVO recipe=recipeService.recipeDetail(id);
		
		model.addAttribute("id", id);
		model.addAttribute("recipe", recipe);
		/*model.addAttribute("contentList", contentList);
		model.addAttribute("ingrList", ingrList);
		model.addAttribute("tagList", tagList);*/
		return "recipe/recipe_detail";
	}
	
	@RequestMapping("recipe/recipe_main_test")
	public String recipeMainTest(){
		
		return "recipe/recipe_main_test";
	}
	
	@RequestMapping("recipe/recipe_tag_list")
	public String recipeTagListByTagName(String tagName, PagingManager page, Model model){
		int totalPage=recipeDAO.recipeTagListTotalPage(tagName);
		
		page.setRowSize(9);
		Map pageCal=page.calcPage(100);//block계산 필요하지 않아 아무수나 우선 넣었다.
		
		Map map=new HashMap();
		map.put("start", pageCal.get("start"));
		map.put("end", pageCal.get("end"));
		map.put("tagName", tagName);
		
		List<RecipeVO> recipeList=recipeDAO.recipeTagListByTagName(map);
		for (RecipeVO vo : recipeList) {
			
			//사용자가 올린 이미지가 아니라 웹에서 가져온 이미지면 oriname을 사용한다.
			vo.setImgAuto();
			
		}
		
		model.addAttribute("list", recipeList);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("page", page.getPage());
		model.addAttribute("tagName", tagName);
		
		return "recipe/recipe_tag_list";
	}
	
	@RequestMapping("recipe/recipe_ingr_list")
	public String recipeIngrListByIngrName(PagingManager page, String ingrName, Model model){
		int total=recipeDAO.recipeIngrListTotal(ingrName);
				
		page.setRowSize(9);
		Map pageCal=page.calcPage(total);
		
		Map map=new HashMap();
		map.put("start", pageCal.get("start"));
		map.put("end", pageCal.get("end"));
		map.put("ingrName", ingrName);
		
		List<RecipeVO> recipeList=recipeDAO.recipeIngrListByIngrName(map);
		for (RecipeVO vo : recipeList) {
			vo.setImgAuto();
			
		}
		System.out.println("recipeList크기는"+recipeList.size());
		
		model.addAttribute("recipeList", recipeList);
		model.addAttribute("totalPage", page.getTotalPage());
		model.addAttribute("page", page.getPage());
		model.addAttribute("ingrName", ingrName);		
		
		return "recipe/recipe_ingr_list";
	}
	
	
	
}
