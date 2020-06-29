package com.omokCSH.omok;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RoomJoin {
	HashMap<String, Integer> roomPlayer = new HashMap<String,Integer>();
	HashMap<String, String> whiteStone = new HashMap<String,String>();
	HashMap<String, String> blackStone = new HashMap<String,String>();
	HashMap<String, String> chat = new HashMap<String,String>();
	// 1,3::2,3::3,3::
	@RequestMapping(value="/joinOmok", method = RequestMethod.GET)
	public String room(Model model, HttpServletRequest request) {
		String num = (String)request.getParameter("g_room");
		model.addAttribute("room",num);
		System.out.print(num);
		
		return "room";
	}
	
	@RequestMapping(value="/joinOmok", method = RequestMethod.POST)
	public String roomPost(Model model, HttpServletRequest request) {
		String num = (String)request.getParameter("g_room");
		if( roomPlayer.containsKey(num)) { //방이 만들어져 있는 경우
			if(roomPlayer.get(num) == 1) { // 방에 1명만 있는 경우
				model.addAttribute("player", 2);
				roomPlayer.put(num,2);
			}
			else if(roomPlayer.get(num) == 2) {// 2명있는경우
				return "fullError";
			}
		}
		else {
				model.addAttribute("player",1);
				roomPlayer.put(num,1);
		}
		model.addAttribute("room",num);
		
		return "room";
	}
	@ResponseBody //방 목록을 표현하기 위한 데이터.
	@RequestMapping(value="/searchRoom", method = RequestMethod.POST)
	public String searchRoom() {
		String ret="";
		Iterator<String> keys= roomPlayer.keySet().iterator();
		while(keys.hasNext()) {
			String key = keys.next();
			ret = ret + key+","+roomPlayer.get(key)+"::";
		}
		System.out.println(ret);
		return ret;
	}
	
	@ResponseBody
	@RequestMapping(value="/putStone", method = RequestMethod.POST)
	public String putStone(CliDataDTO mydata) {
		String num = mydata.getRoom();
		if(!blackStone.containsKey(num)) {
			blackStone.put(num,"::");
		}
		if(!whiteStone.containsKey(num)) {
			whiteStone.put(num,"::");
		}
		String blacks = blackStone.get(num);
		String whites = whiteStone.get(num);
		if(mydata.getRefresh().equals("1")) {
			return blacks+"@@"+whites;
		}
		String loc = mydata.getLocx()+","+mydata.getLocy();

		int cntB = blacks.split("::").length;
		int cntW = whites.split("::").length;
		System.out.println(loc+"??"+blacks+"??"+whites);
		if(!(blacks+"::"+whites).contains(loc)) {//중복체크
			if(cntW == cntB) { // 개수가 같으면 블랙(player1번) 차례
				if(mydata.getPlayer().equals("1")) { // 본인 차례 맞을경우
					blacks = blacks+loc+"::";
					blackStone.put(mydata.getRoom(),blacks);
				}
				else {} // 본인 차례 아닐 경우
			}
			else { // 아니면 화이트(2번) 차례
				if(mydata.getPlayer().equals("2")) { // 본인 차례 맞을경우
					whites = whites+loc+"::";
					whiteStone.put(mydata.getRoom(),whites);
				}
				else {} // 본인 차례 아닐 경우
			}

		}
		System.out.println(blacks+"@@"+whites);
		return blacks+"@@"+whites+"@@";
	}
	
	
}
