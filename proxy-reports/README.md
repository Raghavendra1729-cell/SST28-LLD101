Proxy — Secure & Lazy-Load Reports (Refactoring)
------------------------------------------------
Narrative (Current Code)
A small CLI tool called CampusVault opens internal reports for different users.
Right now, ReportViewer talks directly to ReportFile and eagerly loads the report content every time.

Problems in the current design:
- No access control: any user can open any report.
- No lazy loading: expensive file loading happens immediately on each open.
- No caching: the same report may be loaded multiple times unnecessarily.
- Clients depend directly on the concrete implementation.

Your Task
1) Introduce a Report abstraction.
2) Keep the expensive file-reading logic inside a real subject (for example, RealReport).
3) Add a ReportProxy that:
   - checks whether the user is allowed to access the report
   - lazy-loads the real report only when needed
   - reuses the loaded real report for repeated views through the same proxy
4) Update ReportViewer / App so clients use the proxy instead of directly using the concrete file loader.

Acceptance Criteria
- Unauthorized users cannot view restricted reports.
- Real report loading happens only when access is granted.
- Real report content is loaded lazily (not during proxy construction).
- Repeated views of the same report through the same proxy should not reload the file every time.
- Output remains easy to verify from console logs.

Hints
- Define an interface: Report { void display(User user); }
- Let RealReport do the expensive load.
- Let ReportProxy hold metadata + a nullable RealReport reference.
- Add logs so it is obvious whether a report was really loaded.

Build & Run
  cd proxy-reports/src
  javac com/example/reports/*.java
  java com.example.reports.App

Repo intent
This is a refactoring assignment: the starter code works, but it does not use Proxy properly.
Students should refactor the design so access control + lazy loading happen via a proxy.


1. The Problem (Old Code)
No Security: Anyone could open any confidential report.

Too Slow: The 120ms "disk load" happened every single time a report was opened.

No Memory: Opening the same report 3 times caused 3 slow disk loads.

2. The Fix (Proxy Pattern)
The "Bouncer" (ReportProxy): Sits between the user and the heavy report.

Access Control: Checks roles and blocks unauthorized users instantly.

Lazy Load + Cache: Waits to create the heavy RealReport until it's actually needed, then saves it in a variable to reuse instantly next time.

3. The Code Changes
Moved the heavy loading logic into a new RealReport class.

Built ReportProxy to handle the security and caching checks.

Updated App.java to create proxies instead of direct files, then deleted the old ReportFile.