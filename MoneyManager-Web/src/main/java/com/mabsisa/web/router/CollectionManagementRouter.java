/**
 * 
 */
package com.mabsisa.web.router;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mabsisa.common.model.CollectionDetail;
import com.mabsisa.common.utils.CommonUtils;
import com.mabsisa.service.collectionmanagement.CollectionManagementService;

/**
 * @author abhinab
 *
 */
@Controller
@RequestMapping("/collection")
public class CollectionManagementRouter {
	
	@Autowired
	private CollectionManagementService collectionManagementService;
	
	@GetMapping("/view")
	public String view(Model model) {
		List<CollectionDetail> collectionDetails = new ArrayList<CollectionDetail>();
		try {
			collectionDetails = collectionManagementService.retrieveCollectionDetails();
		} catch(Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("collectionDetails", collectionDetails);
		model.addAttribute("access", CommonUtils.getLoggedInUserAccess());
		return "collectionmanagement/listcollection";
	}

}
