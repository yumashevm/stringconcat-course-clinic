package com.stringconcat.dev.course.common.types.base

abstract class AggregateRoot<T>(id: T, version: Version) : DomainEntity<T>(id, version)