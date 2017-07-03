package com.sist.recipe;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sist.vo.IngredientVO;
import com.sist.vo.ReligionVO;

@Service
public class RecipeService {
	@Autowired
	private IngredientMapper ingrMapper;
	
	public int selectIngrTotal() {
		return ingrMapper.selectIngrTotal();
	}
	
	public List<IngredientVO> selectIngrList(Map map) {
		return ingrMapper.selectIngrList(map);
	}
	
	@Transactional
	public void insertIngr(IngredientVO vo) {
		Map<String, String> map = new HashMap();
		int result = ingrMapper.insertIngr(vo);
		for (int i : vo.getSeason()) {
			Map season = new HashMap();
			season.put("ingr_id", vo.getId());
			season.put("month", i);
			ingrMapper.insertIngrSeason(season);
		}
		if (result!=0) {
			map.put("result", "y");
		} else {
			map.put("result", "n");
		}
	}
	
	public List<ReligionVO> selectCatInfo(Map map) {
		return ingrMapper.selectCatInfo(map);
	}
	
	@Transactional
	public void modifyCatInfo(String cat, String[] insert, String[] delete) {
		System.out.println(cat);
		if (cat.equals("religion")) {
			for (String name : insert) {
				if (!name.trim().isEmpty()) { ingrMapper.insertReligion(name.trim()); }
			}
			for (String id : delete) {
				if (!id.trim().equals("")) { ingrMapper.deleteReligion(Integer.parseInt(id.trim())); }
			}
			
		} else if (cat.equals("vegeterian")) {
			for (String name : insert) {
				if (!name.trim().equals("")) { ingrMapper.insertVegeterian(name); }
			}
			for (String id : delete) {
				if (!id.trim().equals("")) { ingrMapper.deleteVegeterian(Integer.parseInt(id)); }
			}
		}
		
	}
	
}