/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisServer;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.samples.redis.service.RedisService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 */
@Controller
@RequestMapping("/redis")
public class RedisController {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
    private final ClinicService clinicService;
    
    @Autowired
    private RedisService redisService;

    @Autowired
    public RedisController(ClinicService clinicService) {
        this.clinicService = clinicService;
    }

    @RequestMapping(value = "/test/{id}/{num}", method = RequestMethod.GET)
    public void processCreationForm(@PathVariable("id") String id,@PathVariable("num") Integer num) {
    	String retId = redisService.get(id);	
    	String retId1 = redisService.get(id,num);	
    	logger.info("retId :: {}",retId);
    	logger.info("retId1 :: {}",retId1);
    }

    @RequestMapping(value = "/pets/{petId}/edit", method = RequestMethod.GET)
    public String initUpdateForm(@PathVariable("petId") int petId, ModelMap model) {
        Pet pet = this.clinicService.findPetById(petId);
        model.put("pet", pet);
        return "pets/createOrUpdatePetForm";
    }
    
    @RequestMapping(value = "/pets/{petId}/edit", method = RequestMethod.POST)
    public String processUpdateForm(@Valid Pet pet, BindingResult result, Owner owner, ModelMap model) {
        if (result.hasErrors()) {
            model.put("pet", pet);
            return "pets/createOrUpdatePetForm";
        } else {
            owner.addPet(pet);
            this.clinicService.savePet(pet);
            return "redirect:/owners/{ownerId}";
        }
    }

}
