package io.vertx.up.rs.router;

import io.vertx.ext.web.Route;
import io.vertx.ext.web.RoutingContext;
import io.vertx.up.rs.Sentry;
import io.vertx.up.rs.dispatch.ModeSplitter;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

interface Info {

    String NULL_EVENT = "[ ZERO ] ( {0} ) The system found \"null\" event in the queue. ";
}

interface Pool {
    ConcurrentMap<String, ModeSplitter> THREADS
            = new ConcurrentHashMap<>();
    ConcurrentMap<String, Sentry<RoutingContext>> VERIFIERS
            = new ConcurrentHashMap<>();

    ConcurrentMap<String, Hub<Route>> URIHUBS
            = new ConcurrentHashMap<>();

    ConcurrentMap<String, Hub<Route>> MEDIAHUBS
            = new ConcurrentHashMap<>();
}
