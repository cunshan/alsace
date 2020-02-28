package com.alsace.framework.common.basic;

import com.github.pagehelper.PageHelper;

/**
 * service基类
 */
public class PageQueryService {

  protected <P extends BasePageParam> void startPageQuery(P param){
    PageHelper.startPage(param.getPageNum(),param.getPageSize());
  }
  protected <P extends BasePageParam> void startPageQuery(P param,boolean countSql){
    PageHelper.startPage(param.getPageNum(),param.getPageSize(),countSql);
  }

  protected <P extends BasePageParam> void startPageQuery(P param,String orderBy){
    PageHelper.startPage(param.getPageNum(),param.getPageSize(),orderBy);
  }

  protected <P extends BasePageParam> void startPageQuery(P param,String orderBy,boolean countSql){
    PageHelper.startPage(param.getPageNum(),param.getPageSize(),countSql).setOrderBy(orderBy);
  }
}
