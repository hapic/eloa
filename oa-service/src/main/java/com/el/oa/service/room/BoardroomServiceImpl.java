/*
 * Powered By [rapid-framework]
 * Web Site: #
 * By:eloa
 * Since 2015 - 2016
 */


package com.el.oa.service.room;

import java.util.Map;

import com.el.oa.iservice.common.IBaseService;
import com.el.oa.service.common.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.el.oa.domain.room.Boardroom;
import com.el.oa.domain.room.BoardroomExample;


@Service("boardroomService")
@Transactional
public class BoardroomServiceImpl extends BaseService<Boardroom, BoardroomExample> implements IBaseService<Boardroom, BoardroomExample> {
	

}
