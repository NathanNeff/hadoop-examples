// Define test data
val orig_data = Array(
	"1.2.3.4 - 12345 \"[1/1/2017 12:00:00]\" \"/some.jpg GET\" 200 9999",
	"1.2.3.4 - 12345 \"[1/1/2017 12:00:02]\" \"/home.html GET\" 200 9997",
    "trash",
    "1.2.3.5 - aaaaa"
)

// case class to mimic data
case class Weblog(ip:String, userid:String, req_ts:String)

// Define regex to parse test data into case class (only ip, userid and req_ts
// for now)
val regex = """(.*) - (\d+) \"\[(.+)\]\".*""".r

// Fancy print/debug function
def printWeblog(weblog:Weblog) =
	println(s"""Data: $weblog 
				Class: ${weblog.getClass}
				IP:${weblog.ip}
				Userid:${weblog.userid}
				Request Timestamp:${weblog.req_ts} 
				----------------""")

// Go!
val data = sc.parallelize(orig_data)
val weblogs = data.map{
	case regex(ip, userid, req_ts) => 
            Weblog(ip, userid, req_ts)
    case line =>
			Console.err.println(s"Unexpected line: $line")
			Weblog("error", line, "")
}

val errors = weblogs.filter(wl => wl.ip == "error")
val not_errors = weblogs.filter(wl => wl.ip != "error")

println("These are errors")
for (r <- errors.collect()) {
		printWeblog(r)
}

println("These are NOT errors")
for (r <- not_errors.collect()) {
		printWeblog(r)
}

