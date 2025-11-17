import http from "k6/http";
import { sleep, check } from "k6";

export const options = {
    vus: __ENV.VUS ? Number(__ENV.VUS) : 50,
    duration: __ENV.DURATION ? __ENV.DURATION : "30s",
};

let courseIds = [];

export function setup() {
    const BASE_URL = `${__ENV.HOST}/api/v1/courses`;

    const res = http.get(BASE_URL);

    if (res.status !== 200) {
        throw new Error(`Failed to load courses. Status: ${res.status}`);
    }

    const body = JSON.parse(res.body);

    courseIds = body.map(c => c.id);

    if (courseIds.length === 0) {
        throw new Error("No courses found. Cannot run load test.");
    }

    return { courseIds };
}

export default function (data) {
    const API = `${__ENV.HOST}/api/v1/courses`;
    const CACHE_MODE = __ENV.CACHE_MODE || "NONE";

    const ids = data.courseIds;

    const id = ids[Math.floor(Math.random() * ids.length)];

    const res = http.get(`${API}/${id}?cacheMode=${CACHE_MODE}`);

    check(res, {
        "status is 200": r => r.status === 200,
        "latency < 200ms": r => r.timings.duration < 200,
    });

    sleep(0.1);
}
