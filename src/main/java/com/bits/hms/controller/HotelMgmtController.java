package com.bits.hms.controller;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bits.hms.constants.HotelMgmtConstants;
import com.bits.hms.entity.Hotel;
import com.bits.hms.entity.HotelRequest;
import com.bits.hms.exception.HmsException;
import com.bits.hms.repository.HotelRepository;
import com.bits.hms.util.HmsUtil;

import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin
@RequestMapping("hotel")
@Slf4j
public class HotelMgmtController {

	@Autowired
	HotelRepository hotelRepository;
//	private UserRepository userRepo;

	@GetMapping("/getHotels")
	public List<Hotel> getHotels() {
		return hotelRepository.findAll();
	}

	@PostMapping("/add")
	public String addHotel(@RequestBody HotelRequest hotelRequest) {

		Hotel hotel = new Hotel();
		hotel.setId(UUID.randomUUID().toString());
		hotel.setName(hotelRequest.getName());
		hotel.setCity(hotelRequest.getCity());
		hotel.setState(hotelRequest.getState());

		hotelRepository.saveAndFlush(hotel);

		return hotel.getId() + HotelMgmtConstants.ADD_SUCCESS;
	}

	@DeleteMapping("/delete/{hotelId}")
	public String deleteHotel(@PathVariable("hotelId") String hotelId) throws HmsException {
		if (StringUtils.isEmpty(hotelId)) {
			log.info("empty hotelId");
			throw new HmsException(HttpStatus.INTERNAL_SERVER_ERROR.toString());
		}

		if (HmsUtil.checkForValidUUID(hotelId)) {
			hotelRepository.deleteById(hotelId);
		}

		return hotelId + HotelMgmtConstants.DELETE_SUCCESS;
	}

	@PatchMapping("/update/{hotelId}")
	public String patchHotel(@PathVariable("hotelId") String hotelId, @RequestBody HotelRequest hotelRequest) throws HmsException {
		if (StringUtils.isEmpty(hotelId)) {
			log.info("empty hotelId");
			throw new HmsException(HttpStatus.INTERNAL_SERVER_ERROR.toString());
		}

		if (HmsUtil.checkForValidUUID(hotelId)) {
			Hotel existingHotel = hotelRepository.findById(hotelId).get();
			if (null != existingHotel) {
				if (HmsUtil.checkForNotNullAndEmpty(hotelRequest.getName())) {
					existingHotel.setName(hotelRequest.getName());
				}

				if (HmsUtil.checkForNotNullAndEmpty(hotelRequest.getCity())) {
					existingHotel.setCity(hotelRequest.getCity());
				}

				if (HmsUtil.checkForNotNullAndEmpty(hotelRequest.getState())) {
					existingHotel.setState(hotelRequest.getState());
				}

				hotelRepository.saveAndFlush(existingHotel);
			}

		}

		return hotelId + HotelMgmtConstants.PATCH_SUCCESS;
	}

	@GetMapping("/getHotelsWithMultipleFilter")
	@ResponseBody
	public Object getHotelsWithMultipleFilter(@RequestParam("city") String[] cities, Pageable pageable) {

		if (null != cities) {

			List<String> citiesList = Arrays.asList(cities);

			return hotelRepository.findByCityIn(citiesList, pageable);

		}

		return null;
	}
}