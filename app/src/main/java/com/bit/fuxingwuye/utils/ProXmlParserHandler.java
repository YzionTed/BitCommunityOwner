package com.BIT.fuxingwuye.utils;


import com.BIT.fuxingwuye.bean.CityBean;
import com.BIT.fuxingwuye.bean.DistrictBean;
import com.BIT.fuxingwuye.bean.ProvinceBean;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;


public class ProXmlParserHandler extends DefaultHandler {

	/**
	 * 存储所有的解析对象
	 */
	private List<ProvinceBean> provinceList = new ArrayList<ProvinceBean>();

	public ProXmlParserHandler() {

	}

	public List<ProvinceBean> getDataList() {
		return provinceList;
	}

	@Override
	public void startDocument() throws SAXException {
		// 当读到第一个开始标签的时候，会触发这个方法
	}

	ProvinceBean provinceBean = new ProvinceBean();
	CityBean cityBean = new CityBean();
	DistrictBean districtBean = new DistrictBean();

	@Override
	public void startElement(String uri, String localName, String qName,
							 Attributes attributes) throws SAXException {
		// 当遇到开始标记的时候，调用这个方法
		if (qName.equals("province")) {
			provinceBean = new ProvinceBean();
			provinceBean.setName(attributes.getValue(0));
			provinceBean.setCityList(new ArrayList<CityBean>());
		} else if (qName.equals("city")) {
			cityBean = new CityBean();
			cityBean.setName(attributes.getValue(0));
			cityBean.setDistrictList(new ArrayList<DistrictBean>());
		} else if (qName.equals("district")) {
			districtBean = new DistrictBean();
			districtBean.setName(attributes.getValue(0));
			districtBean.setZipcode(attributes.getValue(1));
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		// 遇到结束标记的时候，会调用这个方法
		if (qName.equals("district")) {
			cityBean.getDistrictList().add(districtBean);
		} else if (qName.equals("city")) {
			provinceBean.getCityList().add(cityBean);
		} else if (qName.equals("province")) {
			provinceList.add(provinceBean);
		}
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
	}

}
