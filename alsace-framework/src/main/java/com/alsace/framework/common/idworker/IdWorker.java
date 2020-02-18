package com.alsace.framework.common.idworker;

/**
 * id生产器
 */
public class IdWorker {

  private static final Sequence worker = new Sequence(); //序列生产器

  /**
   * 获取下一个id.
   */
  public static long getId() {
    return worker.nextId();
  }
}
