<#macro registrationLayout bodyClass="" displayInfo=false displayMessage=true displayRequiredFields=false showAnotherWayIfPresent=true>
<html>
<head>
    <title>Noderunners - Podcasts and Videos by Bitcoin Maxis running Nodes</title>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <meta name="description" content="">
    <meta name="keywords" content="">


    <!--  Essential META Tags -->
    <meta property="og:title" content="Noderunners - Podcasts and Videos by Bitcoin Maxis running Nodes">
    <meta property="og:type" content="article" />
    <meta property="og:image" content="https://noderunners.network/images/noderunners.png">
    <meta property="og:url" content="https://noderunners.network/en/auth/login">
    <meta name="twitter:card" content="summary_large_image">

    <!--  Non-Essential, But Recommended -->
    <meta property="og:description" content="">
    <meta property="og:site_name" content="Noderunners">
    <meta name="twitter:image:alt" content="Alt text for image">

    <link rel="icon" type="image/x-icon" href="/favicon.ico">
    <link rel="icon" type="image/png" sizes="32x32" href="/favicon-32x32.png">
    <link rel="icon" type="image/png" sizes="16x16" href="/favicon-16x16.png">

    <link rel="stylesheet" href="https://noderunners.network/assets/css/bootstrap.min.css">

    <!--<link rel="stylesheet" href="https://noderunners.network/assets/css/main.css">-->
    <link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css" integrity="sha384-AYmEC3Yw5cVb3ZcuHtOA93w35dYTsvhLPVnYs9eStHfGJvOvKxVfELGroGkvsg+p" crossorigin="anonymous"/>
    <!--[if lte IE 9]><link rel="stylesheet" href="https://noderunners.network/assets/css/ie9.css" /><![endif]-->

    <link rel="stylesheet" href="https://noderunners.network/assets/js/jquery-ui-1.11.4/jquery-ui.css">
    <link rel="stylesheet" href="https://noderunners.network/assets/css/pretty-checkbox.min.css">

    <link rel="stylesheet" href="https://noderunners.network/themes/noderunners/css/style.css">
    <link rel="stylesheet" href="https://noderunners.network/assets/css/default.css">
</head>

<body>
    <div id="pageWrapper">
        <div class="bodyWrapper"> <!-- START BODY WRAPPER -->
            <div id="top">
                <div class="container">
                    <div class="row">
                        <div class="col-md-4 col-xs-4">
                            <div class='leftItems topItems desktop'>
                                <ul>
                                    <li><a href="https://noderunners.network/en/recent" class="top-link">Recent</a></li><li><a href="https://noderunners.network/en/popular" class="top-link">Popular</a></li><li><a href="https://noderunners.network/en/top-100" class="top-link">Top 100</a></li><li><a href="https://noderunners.network/en/search/michael-saylor" class="top-link">Giga Chad</a></li>						</ul>
                            </div>
                        </div>
                        <div class="col-md-5 col-xs-12">
                            <div class='rightItems topItems'>
                                <ul>
                                    <li><a href="https://noderunners.network/en/register" class="top-link"><span class="fa fa-user"></span> Register</a></li><li><a href="https://noderunners.network/en/auth/login" class="top-link"><span class="fa fa-lock"></span> Login</a></li>						</ul>
                            </div>
                        </div>
                        <div class="col-md-3 col-xs-12">
                            <div class="quicksearch">
                                <div class="searchBar">
                                    <form action="https://noderunners.network/en/search" method="post" accept-charset="utf-8">
                                        <input type="text" name="q" value=""  required="required" placeholder="Search..." autocomplete="off" />
                                        <input type="submit" name="Search" value=""  />
                                        <span class='fa fa-search'></span></form>						</div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div id="topBar">
                <div class="container">
                    <div class="row">
                        <div class="col-md-3 col-xs-6" data-nav='Topmenu'>
                            <a href="https://noderunners.network/en/" id="logo"><svg id="logo-img" data-name="Logo Noderunners" xmlns="https://www.w3.org/2000/svg" viewBox="0 0 467.38 90.44"><defs><style>.cls-1{fill:#000000;}</style></defs><path class="cls-1" d="M200.47,36.7c9.28,0,14.36,6.8,14.36,15.38,0,1-.19,3-.19,3H192.27A9.32,9.32,0,0,0,202,63.77a14.73,14.73,0,0,0,9.09-3.63l3.37,5.59a19.71,19.71,0,0,1-13,4.9c-10.73,0-17.4-7.75-17.4-17C184,43.69,190.75,36.7,200.47,36.7Zm6.1,12.77c-.13-3.87-2.8-6.54-6.1-6.54a7.76,7.76,0,0,0-7.94,6.54Z"/><path class="cls-1" d="M222.66,45.72a1.24,1.24,0,0,0-1.4-1.39h-2.54V37.46h7.37c2.86,0,4.32,1.21,4.32,3.94v1.91a15.49,15.49,0,0,1-.12,2h.12c1.46-4.63,5.34-8.32,10.29-8.32a10.08,10.08,0,0,1,1.46.13v7.94a13.85,13.85,0,0,0-2-.13,9.12,9.12,0,0,0-8.83,6.8,19.36,19.36,0,0,0-.7,5.27V69.86h-8Z"/><path class="cls-1" d="M249.1,45.72a1.24,1.24,0,0,0-1.39-1.39h-2.54V37.46h7.68c3,0,4.26,1.34,4.26,4.26V56.65c0,3.94,1,6.61,5,6.61,5.84,0,9.08-5.15,9.08-10.93V37.46h8.07V61.67a1.27,1.27,0,0,0,1.4,1.4h2.54v6.79h-7.43c-2.8,0-4.26-1.33-4.26-3.62v-.89c0-.76.06-1.46.06-1.46h-.12a12.25,12.25,0,0,1-11.18,6.74c-6.93,0-11.19-3.5-11.19-12.33Z"/><path class="cls-1" d="M289.78,45.72a1.24,1.24,0,0,0-1.4-1.39h-2.54V37.46h7.44c2.86,0,4.25,1.34,4.25,3.63V42a9.5,9.5,0,0,1-.12,1.4h.12A12.48,12.48,0,0,1,309,36.7c7.18,0,11.31,3.75,11.31,12.33V61.67a1.27,1.27,0,0,0,1.39,1.4h2.55v6.79h-7.69c-3,0-4.32-1.27-4.32-4.31V50.68c0-3.94-1-6.61-5.08-6.61a8.9,8.9,0,0,0-8.71,6.61,13.76,13.76,0,0,0-.63,4.32V69.86h-8Z"/><path class="cls-1" d="M330.66,45.72a1.24,1.24,0,0,0-1.4-1.39h-2.54V37.46h7.43c2.86,0,4.26,1.34,4.26,3.63V42a8.79,8.79,0,0,1-.13,1.4h.13a12.46,12.46,0,0,1,11.43-6.74c7.18,0,11.31,3.75,11.31,12.33V61.67a1.27,1.27,0,0,0,1.4,1.4h2.54v6.79H357.4c-3,0-4.32-1.27-4.32-4.31V50.68c0-3.94-1-6.61-5.08-6.61a8.88,8.88,0,0,0-8.7,6.61,13.48,13.48,0,0,0-.64,4.32V69.86h-8Z"/><path class="cls-1" d="M384.74,36.7c9.28,0,14.36,6.8,14.36,15.38,0,1-.19,3-.19,3H376.55a9.32,9.32,0,0,0,9.72,8.71,14.69,14.69,0,0,0,9.08-3.63l3.37,5.59a19.71,19.71,0,0,1-13,4.9c-10.73,0-17.4-7.75-17.4-17C368.29,43.69,375,36.7,384.74,36.7Zm6.1,12.77c-.13-3.87-2.79-6.54-6.1-6.54a7.76,7.76,0,0,0-7.94,6.54Z"/><path class="cls-1" d="M406.93,45.72a1.24,1.24,0,0,0-1.4-1.39H403V37.46h7.37c2.86,0,4.32,1.21,4.32,3.94v1.91a14.32,14.32,0,0,1-.13,2h.13C416.14,40.71,420,37,425,37a10.08,10.08,0,0,1,1.46.13v7.94a13.85,13.85,0,0,0-2-.13,9.12,9.12,0,0,0-8.83,6.8,19.36,19.36,0,0,0-.7,5.27V69.86h-8Z"/><path class="cls-1" d="M433.25,59.57s4.12,4.77,9.65,4.77c2.48,0,4.39-1,4.39-3.18,0-4.57-17.09-4.51-17.09-15,0-6.48,5.84-9.47,12.57-9.47,4.39,0,11.38,1.46,11.38,6.74V46.8H447V45.22c0-1.53-2.28-2.29-4.06-2.29-2.86,0-4.83,1-4.83,2.92,0,5.08,17.22,4.07,17.22,14.87,0,6.1-5.41,9.91-12.46,9.91a17.78,17.78,0,0,1-13.47-5.78Z"/><path class="cls-1" d="M159.22,36.7c7.05,0,9.34,3.94,9.34,3.94h.12s-.06-.82-.06-1.91V30a1.24,1.24,0,0,0-1.4-1.4h-2.54V21.76h7.69c3,0,4.32,1.33,4.32,4.32V61.67a1.27,1.27,0,0,0,1.4,1.4h2.47v6.79h-7.43c-2.92,0-4-1.39-4-3.3V65.35H169s-2.67,5.28-10.1,5.28c-8.77,0-14.42-6.93-14.42-17C144.48,43.37,150.64,36.7,159.22,36.7Zm9.59,16.9c0-5-2.6-10-8-10-4.45,0-8.13,3.62-8.13,10,0,6.16,3.23,10.16,8,10.16C164.87,63.83,168.81,60.78,168.81,53.6Z"/><circle class="cls-1" cx="161.84" cy="25.1" r="6.2"/><path class="cls-1" d="M122.61,36.7c9.91,0,17.78,7,17.78,17s-7.87,17-17.78,17-17.73-7-17.73-17S112.76,36.7,122.61,36.7Zm0,27.07c5.27,0,9.65-4.07,9.65-10.1s-4.38-10.11-9.65-10.11S113,47.69,113,53.67,117.4,63.77,122.61,63.77Z"/><circle class="cls-1" cx="141.94" cy="25.1" r="6.2"/><path class="cls-1" d="M10.11,61.32H37.27a1.46,1.46,0,0,0,1.46-1.45V25a3.18,3.18,0,0,1,3.18-3.18h4.6L64.26,47.73c1.85,2.71,3.9,6.6,3.9,6.6h.13s-.46-3.89-.46-6.6V26.28c0-3.1,1.32-4.48,4.48-4.48h70.4v7.32H77.92a1.45,1.45,0,0,0-1.45,1.45V65.46a3.19,3.19,0,0,1-3.18,3.19H68.75L50.93,42.78A72.13,72.13,0,0,1,47,36.18h-.13s.46,3.89.46,6.6V64.16a4.49,4.49,0,0,1-4.49,4.49H10.11Z"/><path class="cls-1" d="M57.6,89.05a43.83,43.83,0,1,1,43.82-43.83A43.88,43.88,0,0,1,57.6,89.05Zm0-79.65A35.83,35.83,0,1,0,93.42,45.22,35.86,35.86,0,0,0,57.6,9.4Z"/><circle class="cls-1" cx="7.95" cy="64.76" r="6.2"/><rect x="79.3" y="76.13" width="379.91" height="6.91"/><circle class="cls-1" cx="459.42" cy="79.58" r="6.2"/></svg></a>
                        </div>
                        <div class="col-md-9 col-xs-3">   </div>
                    </div>
                </div>
            </div>

            <section style="margin-top:50px;">
                <div class="container">
                    <div class="col-md-6 col-md-offset-3">
                        <div class="login">
                            <h1 class="h3">Login with your Lightning Wallet</h1>

                            <#nested "form">

                            <#if auth?has_content && auth.showTryAnotherWayLink() && showAnotherWayIfPresent>
                                <form id="kc-select-try-another-way-form" action="${url.loginAction}" method="post">
                                    <div class="${properties.kcFormGroupClass!}">
                                        <input type="hidden" name="tryAnotherWay" value="on"/>
                                        <a href="#" id="try-another-way"
                                           onclick="document.forms['kc-select-try-another-way-form'].submit();return false;">${msg("doTryAnotherWay")}</a>
                                    </div>
                                </form>
                            </#if>

                        </div>
                    </div>
                </div>
            </section>
        </div>
    </div>

    <section id="footer">
        <div class="container">
            <div class="row">
                <div class="col-md-10 col-md-offset-1">
                    <div class="disclaimer centered">
                        <p><strong>Noderunners</strong> is a project of 21 Toxic Bitcoin Maximalists and is always under development.<br>
                            When you don&#39;t agree with any of the content or with our policy please give us a call on 1-800-CRY-HARDER.</p>

                        <p><strong>Non of the content on this website is Financial Advice.</strong><br>
                            Always Do Your Own Research (DYOR) before converting your worthless Fiat Money into the Hardest Money ever known to mankind.</p>
                    </div>
                    <div class="copyright">
                        <ul data-nav="Footerlinks" class="list-inline">
                            <li>&copy; 2022 Noderunners</li>
                        </ul>
                    </div>
                </div>

            </div>
        </div>
    </section>

    </div>
</div>

</body>

</html>
</#macro>