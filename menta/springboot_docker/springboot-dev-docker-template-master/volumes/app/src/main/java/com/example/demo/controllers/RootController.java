package com.example.demo.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.models.InquiryForm;
import com.example.demo.models.InquiryForm3;
import com.example.demo.models.ItemForm;
import com.example.demo.repositries.InquiryRepository;
import com.example.demo.repositries.InquiryRepository3;
import com.example.demo.repositries.ItemRepository;
import com.example.demo.repositries.listRepository;

@Controller
@RequestMapping("/")
public class RootController {

	@Autowired
	InquiryRepository repository;
	
//	@Autowired
//	InquiryRepository2 repository2;
	
	@Autowired
	InquiryRepository3 repository3;
	
	@Autowired //@Autowiredを書くことで、他クラスとの繋がりを宣言する記述が大幅に減る
	ItemRepository itemrepository;
	
	@Autowired
	listRepository listrepository;
	
	
	

	@GetMapping
	public String index() {
		return "root/index";
	}

	@GetMapping("/form")
	public String form(InquiryForm inquiryForm) {
		return "root/form";
	}
	
//	@GetMapping ("/form2")
//	public String form2(InquiryForm inquiryForm2) {
//		return "root/form2";
//	}
//	
	@GetMapping ("/form3")
	public String form3(InquiryForm3 inquiryForm3) {
		return "root/form3";
	}
	
	@GetMapping ("/item")
	public String item(ItemForm itemForm) {
		return "root/item";
	}
	
	@GetMapping ("/list")
	public String list(Model model) { 
        List<ItemForm> itemlist = itemrepository.findAll();
        model.addAttribute("itemlist", itemlist); 
        return "root/list";
    }
	
//	optionalクラスの値をジェネリクス型をコンバーターを使って取り出すことができました。
//	 Optional<InquiryForm> inqForm = Optional.ofNullable(repository.findById(id).get());
//	                 InquiryForm inq2 =  inqForm.get();
//	                 model.addAttribute("InquiryForm", inq2);
//	 Optional<InquiryForm> inqForm = Optional.ofNullable(repository.findById(id).get());
//  InquiryForm inq2 =  inqForm.get();
//  model.addAttribute("ItemForm", inq2);
//	get()メソッドを使用してInquiryForm型に取り出しています。
//    Optional<ItemForm> inqForm = itemrepository.findAll(ItemForm);
//  ItemForm inq2 =  inqForm.get();
//  Player player = itemrepository.count();
//  model.addAttribute("player", player);
//	 Edit edit = itemrepository.findById(id);
//  model.addAttribute("",edit);
	
	// 一件データを取得し、フォーム内に表示
    @GetMapping("{id}/edit")
    public String edit(@PathVariable String id, Model model) {
        Optional<ItemForm> editForm = itemrepository.findById(id);
        model.addAttribute("editForm", editForm);
        return "{id}/edit";
    }

	
	@PostMapping("/form")
	public String form(@Validated InquiryForm inquiryForm, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "root/form";
		}
		
		
		// RDBと連携できることを確認しておきます。
		repository.saveAndFlush(inquiryForm);
		inquiryForm.clear();
		model.addAttribute("message", "お問い合わせを受け付けました。");
		return "root/form";
	}
	
//	@PostMapping("/form2")
//	public String form2(@Validated InquiryForm inquiryForm2, BindingResult bindingResult, Model model) {
//		if (bindingResult.hasErrors()) {
//			return "root/form2";
//		}
//		
//		
//		// RDBと連携できることを確認しておきます。
//		repository2.saveAndFlush(inquiryForm2);
//		inquiryForm2.clear();
//		model.addAttribute("message", "お問い合わせを受け付けました。");
//		return "root/form2";
//	}
	
	@PostMapping("/form3")
	public String form3(@Validated InquiryForm3 inquiryForm3, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "root/form3";
		}
		
		
		// RDBと連携できることを確認しておきます。
		repository3.saveAndFlush(inquiryForm3);
		inquiryForm3.clear();
		model.addAttribute("message", "お問い合わせを受け付けました。");
		return "root/form3";
	}
	
	@PostMapping("/item")
	public String item(@Validated ItemForm itemForm, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "root/item";
		}
		
		
		// RDBと連携できることを確認しておきます。
		itemrepository.saveAndFlush(itemForm);
		itemForm.clear();
		model.addAttribute("message", "登録が完了しました。");
		return "root/item";
	}
	
	@PostMapping("/list")
	public String list(@ModelAttribute ItemForm itemForm, BindingResult bindingResult, Model model) {
		itemrepository.save(itemForm);
		return "redirect:/list";
	}
	
	@RequestMapping("/{id}/edit")
    public String edit(@ModelAttribute ItemForm itemForm, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "/{id}/edit";
		}
		
		
        itemrepository.saveAndFlush(itemForm);
        itemForm.clear();
		model.addAttribute("message", "編集が完了しました。");        
        return "item/{id}/edit";
    }
	// 商品情報を一件削除し、リダイレクト
    @DeleteMapping("delete/{id}")
    public String delete(@PathVariable int id) {
        itemrepository.deleteAllInBatch();;
        return "redirect:root/list";
    }
}