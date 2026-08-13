(function(){const e=document.createElement("link").relList;if(e&&e.supports&&e.supports("modulepreload"))return;for(const o of document.querySelectorAll('link[rel="modulepreload"]'))r(o);new MutationObserver(o=>{for(const i of o)if(i.type==="childList")for(const s of i.addedNodes)s.tagName==="LINK"&&s.rel==="modulepreload"&&r(s)}).observe(document,{childList:!0,subtree:!0});function t(o){const i={};return o.integrity&&(i.integrity=o.integrity),o.referrerPolicy&&(i.referrerPolicy=o.referrerPolicy),o.crossOrigin==="use-credentials"?i.credentials="include":o.crossOrigin==="anonymous"?i.credentials="omit":i.credentials="same-origin",i}function r(o){if(o.ep)return;o.ep=!0;const i=t(o);fetch(o.href,i)}})();window.Vaadin=window.Vaadin||{};window.Vaadin.featureFlags=window.Vaadin.featureFlags||{};window.Vaadin.featureFlags.exampleFeatureFlag=!1;window.Vaadin.featureFlags.collaborationEngineBackend=!1;window.Vaadin.featureFlags.webPush=!1;window.Vaadin.featureFlags.formFillerAddon=!1;window.Vaadin.featureFlags.reactRouter=!1;const un="modulepreload",pn=function(n,e){return new URL(n,e).href},dt={},qe=function(e,t,r){let o=Promise.resolve();if(t&&t.length>0){const i=document.getElementsByTagName("link");o=Promise.all(t.map(s=>{if(s=pn(s,r),s in dt)return;dt[s]=!0;const l=s.endsWith(".css"),a=l?'[rel="stylesheet"]':"";if(!!r)for(let f=i.length-1;f>=0;f--){const u=i[f];if(u.href===s&&(!l||u.rel==="stylesheet"))return}else if(document.querySelector(`link[href="${s}"]${a}`))return;const h=document.createElement("link");if(h.rel=l?"stylesheet":un,l||(h.as="script",h.crossOrigin=""),h.href=s,document.head.appendChild(h),l)return new Promise((f,u)=>{h.addEventListener("load",f),h.addEventListener("error",()=>u(new Error(`Unable to preload CSS for ${s}`)))})}))}return o.then(()=>e()).catch(i=>{const s=new Event("vite:preloadError",{cancelable:!0});if(s.payload=i,window.dispatchEvent(s),!s.defaultPrevented)throw i})};function $e(n){return n=n||[],Array.isArray(n)?n:[n]}function O(n){return`[Vaadin.Router] ${n}`}function fn(n){if(typeof n!="object")return String(n);const e=Object.prototype.toString.call(n).match(/ (.*)\]$/)[1];return e==="Object"||e==="Array"?`${e} ${JSON.stringify(n)}`:e}const Ae="module",Ce="nomodule",Ge=[Ae,Ce];function ht(n){if(!n.match(/.+\.[m]?js$/))throw new Error(O(`Unsupported type for bundle "${n}": .js or .mjs expected.`))}function Vt(n){if(!n||!R(n.path))throw new Error(O('Expected route config to be an object with a "path" string property, or an array of such objects'));const e=n.bundle,t=["component","redirect","bundle"];if(!V(n.action)&&!Array.isArray(n.children)&&!V(n.children)&&!Te(e)&&!t.some(r=>R(n[r])))throw new Error(O(`Expected route config "${n.path}" to include either "${t.join('", "')}" or "action" function but none found.`));if(e)if(R(e))ht(e);else if(Ge.some(r=>r in e))Ge.forEach(r=>r in e&&ht(e[r]));else throw new Error(O('Expected route bundle to include either "'+Ce+'" or "'+Ae+'" keys, or both'));n.redirect&&["bundle","component"].forEach(r=>{r in n&&console.warn(O(`Route config "${n.path}" has both "redirect" and "${r}" properties, and "redirect" will always override the latter. Did you mean to only use "${r}"?`))})}function ut(n){$e(n).forEach(e=>Vt(e))}function pt(n,e){let t=document.head.querySelector('script[src="'+n+'"][async]');return t||(t=document.createElement("script"),t.setAttribute("src",n),e===Ae?t.setAttribute("type",Ae):e===Ce&&t.setAttribute(Ce,""),t.async=!0),new Promise((r,o)=>{t.onreadystatechange=t.onload=i=>{t.__dynamicImportLoaded=!0,r(i)},t.onerror=i=>{t.parentNode&&t.parentNode.removeChild(t),o(i)},t.parentNode===null?document.head.appendChild(t):t.__dynamicImportLoaded&&r()})}function mn(n){return R(n)?pt(n):Promise.race(Ge.filter(e=>e in n).map(e=>pt(n[e],e)))}function re(n,e){return!window.dispatchEvent(new CustomEvent(`vaadin-router-${n}`,{cancelable:n==="go",detail:e}))}function Te(n){return typeof n=="object"&&!!n}function V(n){return typeof n=="function"}function R(n){return typeof n=="string"}function jt(n){const e=new Error(O(`Page not found (${n.pathname})`));return e.context=n,e.code=404,e}const X=new class{};function gn(n){const e=n.port,t=n.protocol,i=t==="http:"&&e==="80"||t==="https:"&&e==="443"?n.hostname:n.host;return`${t}//${i}`}function ft(n){if(n.defaultPrevented||n.button!==0||n.shiftKey||n.ctrlKey||n.altKey||n.metaKey)return;let e=n.target;const t=n.composedPath?n.composedPath():n.path||[];for(let l=0;l<t.length;l++){const a=t[l];if(a.nodeName&&a.nodeName.toLowerCase()==="a"){e=a;break}}for(;e&&e.nodeName.toLowerCase()!=="a";)e=e.parentNode;if(!e||e.nodeName.toLowerCase()!=="a"||e.target&&e.target.toLowerCase()!=="_self"||e.hasAttribute("download")||e.hasAttribute("router-ignore")||e.pathname===window.location.pathname&&e.hash!==""||(e.origin||gn(e))!==window.location.origin)return;const{pathname:o,search:i,hash:s}=e;re("go",{pathname:o,search:i,hash:s})&&(n.preventDefault(),n&&n.type==="click"&&window.scrollTo(0,0))}const vn={activate(){window.document.addEventListener("click",ft)},inactivate(){window.document.removeEventListener("click",ft)}},yn=/Trident/.test(navigator.userAgent);yn&&!V(window.PopStateEvent)&&(window.PopStateEvent=function(n,e){e=e||{};var t=document.createEvent("Event");return t.initEvent(n,!!e.bubbles,!!e.cancelable),t.state=e.state||null,t},window.PopStateEvent.prototype=window.Event.prototype);function mt(n){if(n.state==="vaadin-router-ignore")return;const{pathname:e,search:t,hash:r}=window.location;re("go",{pathname:e,search:t,hash:r})}const _n={activate(){window.addEventListener("popstate",mt)},inactivate(){window.removeEventListener("popstate",mt)}};var Z=Gt,bn=Qe,wn=An,Sn=zt,En=qt,Bt="/",Ht="./",$n=new RegExp(["(\\\\.)","(?:\\:(\\w+)(?:\\(((?:\\\\.|[^\\\\()])+)\\))?|\\(((?:\\\\.|[^\\\\()])+)\\))([+*?])?"].join("|"),"g");function Qe(n,e){for(var t=[],r=0,o=0,i="",s=e&&e.delimiter||Bt,l=e&&e.delimiters||Ht,a=!1,c;(c=$n.exec(n))!==null;){var h=c[0],f=c[1],u=c.index;if(i+=n.slice(o,u),o=u+h.length,f){i+=f[1],a=!0;continue}var m="",L=n[o],H=c[2],ue=c[3],Ie=c[4],C=c[5];if(!a&&i.length){var P=i.length-1;l.indexOf(i[P])>-1&&(m=i[P],i=i.slice(0,P))}i&&(t.push(i),i="",a=!1);var z=m!==""&&L!==void 0&&L!==m,W=C==="+"||C==="*",Ne=C==="?"||C==="*",I=m||s,pe=ue||Ie;t.push({name:H||r++,prefix:m,delimiter:I,optional:Ne,repeat:W,partial:z,pattern:pe?Cn(pe):"[^"+N(I)+"]+?"})}return(i||o<n.length)&&t.push(i+n.substr(o)),t}function An(n,e){return zt(Qe(n,e))}function zt(n){for(var e=new Array(n.length),t=0;t<n.length;t++)typeof n[t]=="object"&&(e[t]=new RegExp("^(?:"+n[t].pattern+")$"));return function(r,o){for(var i="",s=o&&o.encode||encodeURIComponent,l=0;l<n.length;l++){var a=n[l];if(typeof a=="string"){i+=a;continue}var c=r?r[a.name]:void 0,h;if(Array.isArray(c)){if(!a.repeat)throw new TypeError('Expected "'+a.name+'" to not repeat, but got array');if(c.length===0){if(a.optional)continue;throw new TypeError('Expected "'+a.name+'" to not be empty')}for(var f=0;f<c.length;f++){if(h=s(c[f],a),!e[l].test(h))throw new TypeError('Expected all "'+a.name+'" to match "'+a.pattern+'"');i+=(f===0?a.prefix:a.delimiter)+h}continue}if(typeof c=="string"||typeof c=="number"||typeof c=="boolean"){if(h=s(String(c),a),!e[l].test(h))throw new TypeError('Expected "'+a.name+'" to match "'+a.pattern+'", but got "'+h+'"');i+=a.prefix+h;continue}if(a.optional){a.partial&&(i+=a.prefix);continue}throw new TypeError('Expected "'+a.name+'" to be '+(a.repeat?"an array":"a string"))}return i}}function N(n){return n.replace(/([.+*?=^!:${}()[\]|/\\])/g,"\\$1")}function Cn(n){return n.replace(/([=!:$/()])/g,"\\$1")}function Wt(n){return n&&n.sensitive?"":"i"}function Tn(n,e){if(!e)return n;var t=n.source.match(/\((?!\?)/g);if(t)for(var r=0;r<t.length;r++)e.push({name:r,prefix:null,delimiter:null,optional:!1,repeat:!1,partial:!1,pattern:null});return n}function xn(n,e,t){for(var r=[],o=0;o<n.length;o++)r.push(Gt(n[o],e,t).source);return new RegExp("(?:"+r.join("|")+")",Wt(t))}function Rn(n,e,t){return qt(Qe(n,t),e,t)}function qt(n,e,t){t=t||{};for(var r=t.strict,o=t.start!==!1,i=t.end!==!1,s=N(t.delimiter||Bt),l=t.delimiters||Ht,a=[].concat(t.endsWith||[]).map(N).concat("$").join("|"),c=o?"^":"",h=n.length===0,f=0;f<n.length;f++){var u=n[f];if(typeof u=="string")c+=N(u),h=f===n.length-1&&l.indexOf(u[u.length-1])>-1;else{var m=u.repeat?"(?:"+u.pattern+")(?:"+N(u.delimiter)+"(?:"+u.pattern+"))*":u.pattern;e&&e.push(u),u.optional?u.partial?c+=N(u.prefix)+"("+m+")?":c+="(?:"+N(u.prefix)+"("+m+"))?":c+=N(u.prefix)+"("+m+")"}}return i?(r||(c+="(?:"+s+")?"),c+=a==="$"?"$":"(?="+a+")"):(r||(c+="(?:"+s+"(?="+a+"))?"),h||(c+="(?="+s+"|"+a+")")),new RegExp(c,Wt(t))}function Gt(n,e,t){return n instanceof RegExp?Tn(n,e):Array.isArray(n)?xn(n,e,t):Rn(n,e,t)}Z.parse=bn;Z.compile=wn;Z.tokensToFunction=Sn;Z.tokensToRegExp=En;const{hasOwnProperty:On}=Object.prototype,Ke=new Map;Ke.set("|false",{keys:[],pattern:/(?:)/});function gt(n){try{return decodeURIComponent(n)}catch{return n}}function Pn(n,e,t,r,o){t=!!t;const i=`${n}|${t}`;let s=Ke.get(i);if(!s){const c=[];s={keys:c,pattern:Z(n,c,{end:t,strict:n===""})},Ke.set(i,s)}const l=s.pattern.exec(e);if(!l)return null;const a=Object.assign({},o);for(let c=1;c<l.length;c++){const h=s.keys[c-1],f=h.name,u=l[c];(u!==void 0||!On.call(a,f))&&(h.repeat?a[f]=u?u.split(h.delimiter).map(gt):[]:a[f]=u&&gt(u))}return{path:l[0],keys:(r||[]).concat(s.keys),params:a}}function Kt(n,e,t,r,o){let i,s,l=0,a=n.path||"";return a.charAt(0)==="/"&&(t&&(a=a.substr(1)),t=!0),{next(c){if(n===c)return{done:!0};const h=n.__children=n.__children||n.children;if(!i&&(i=Pn(a,e,!h,r,o),i))return{done:!1,value:{route:n,keys:i.keys,params:i.params,path:i.path}};if(i&&h)for(;l<h.length;){if(!s){const u=h[l];u.parent=n;let m=i.path.length;m>0&&e.charAt(m)==="/"&&(m+=1),s=Kt(u,e.substr(m),t,i.keys,i.params)}const f=s.next(c);if(!f.done)return{done:!1,value:f.value};s=null,l++}return{done:!0}}}}function Ln(n){if(V(n.route.action))return n.route.action(n)}function In(n,e){let t=e;for(;t;)if(t=t.parent,t===n)return!0;return!1}function Nn(n){let e=`Path '${n.pathname}' is not properly resolved due to an error.`;const t=(n.route||{}).path;return t&&(e+=` Resolution had failed on route: '${t}'`),e}function kn(n,e){const{route:t,path:r}=e;if(t&&!t.__synthetic){const o={path:r,route:t};if(!n.chain)n.chain=[];else if(t.parent){let i=n.chain.length;for(;i--&&n.chain[i].route&&n.chain[i].route!==t.parent;)n.chain.pop()}n.chain.push(o)}}class ae{constructor(e,t={}){if(Object(e)!==e)throw new TypeError("Invalid routes");this.baseUrl=t.baseUrl||"",this.errorHandler=t.errorHandler,this.resolveRoute=t.resolveRoute||Ln,this.context=Object.assign({resolver:this},t.context),this.root=Array.isArray(e)?{path:"",__children:e,parent:null,__synthetic:!0}:e,this.root.parent=null}getRoutes(){return[...this.root.__children]}setRoutes(e){ut(e);const t=[...$e(e)];this.root.__children=t}addRoutes(e){return ut(e),this.root.__children.push(...$e(e)),this.getRoutes()}removeRoutes(){this.setRoutes([])}resolve(e){const t=Object.assign({},this.context,R(e)?{pathname:e}:e),r=Kt(this.root,this.__normalizePathname(t.pathname),this.baseUrl),o=this.resolveRoute;let i=null,s=null,l=t;function a(c,h=i.value.route,f){const u=f===null&&i.value.route;return i=s||r.next(u),s=null,!c&&(i.done||!In(h,i.value.route))?(s=i,Promise.resolve(X)):i.done?Promise.reject(jt(t)):(l=Object.assign(l?{chain:l.chain?l.chain.slice(0):[]}:{},t,i.value),kn(l,i.value),Promise.resolve(o(l)).then(m=>m!=null&&m!==X?(l.result=m.result||m,l):a(c,h,m)))}return t.next=a,Promise.resolve().then(()=>a(!0,this.root)).catch(c=>{const h=Nn(l);if(c?console.warn(h):c=new Error(h),c.context=c.context||l,c instanceof DOMException||(c.code=c.code||500),this.errorHandler)return l.result=this.errorHandler(c),l;throw c})}static __createUrl(e,t){return new URL(e,t)}get __effectiveBaseUrl(){return this.baseUrl?this.constructor.__createUrl(this.baseUrl,document.baseURI||document.URL).href.replace(/[^\/]*$/,""):""}__normalizePathname(e){if(!this.baseUrl)return e;const t=this.__effectiveBaseUrl,r=this.constructor.__createUrl(e,t).href;if(r.slice(0,t.length)===t)return r.slice(t.length)}}ae.pathToRegexp=Z;const{pathToRegexp:vt}=ae,yt=new Map;function Jt(n,e,t){const r=e.name||e.component;if(r&&(n.has(r)?n.get(r).push(e):n.set(r,[e])),Array.isArray(t))for(let o=0;o<t.length;o++){const i=t[o];i.parent=e,Jt(n,i,i.__children||i.children)}}function _t(n,e){const t=n.get(e);if(t&&t.length>1)throw new Error(`Duplicate route with name "${e}". Try seting unique 'name' route properties.`);return t&&t[0]}function bt(n){let e=n.path;return e=Array.isArray(e)?e[0]:e,e!==void 0?e:""}function Mn(n,e={}){if(!(n instanceof ae))throw new TypeError("An instance of Resolver is expected");const t=new Map;return(r,o)=>{let i=_t(t,r);if(!i&&(t.clear(),Jt(t,n.root,n.root.__children),i=_t(t,r),!i))throw new Error(`Route "${r}" not found`);let s=yt.get(i.fullPath);if(!s){let a=bt(i),c=i.parent;for(;c;){const m=bt(c);m&&(a=m.replace(/\/$/,"")+"/"+a.replace(/^\//,"")),c=c.parent}const h=vt.parse(a),f=vt.tokensToFunction(h),u=Object.create(null);for(let m=0;m<h.length;m++)R(h[m])||(u[h[m].name]=!0);s={toPath:f,keys:u},yt.set(a,s),i.fullPath=a}let l=s.toPath(o,e)||"/";if(e.stringifyQueryParams&&o){const a={},c=Object.keys(o);for(let f=0;f<c.length;f++){const u=c[f];s.keys[u]||(a[u]=o[u])}const h=e.stringifyQueryParams(a);h&&(l+=h.charAt(0)==="?"?h:`?${h}`)}return l}}let wt=[];function Un(n){wt.forEach(e=>e.inactivate()),n.forEach(e=>e.activate()),wt=n}const Dn=n=>{const e=getComputedStyle(n).getPropertyValue("animation-name");return e&&e!=="none"},Fn=(n,e)=>{const t=()=>{n.removeEventListener("animationend",t),e()};n.addEventListener("animationend",t)};function St(n,e){return n.classList.add(e),new Promise(t=>{if(Dn(n)){const r=n.getBoundingClientRect(),o=`height: ${r.bottom-r.top}px; width: ${r.right-r.left}px`;n.setAttribute("style",`position: absolute; ${o}`),Fn(n,()=>{n.classList.remove(e),n.removeAttribute("style"),t()})}else n.classList.remove(e),t()})}const Vn=256;function De(n){return n!=null}function jn(n){const e=Object.assign({},n);return delete e.next,e}function T({pathname:n="",search:e="",hash:t="",chain:r=[],params:o={},redirectFrom:i,resolver:s},l){const a=r.map(c=>c.route);return{baseUrl:s&&s.baseUrl||"",pathname:n,search:e,hash:t,routes:a,route:l||a.length&&a[a.length-1]||null,params:o,redirectFrom:i,getUrl:(c={})=>we(M.pathToRegexp.compile(Qt(a))(Object.assign({},o,c)),s)}}function Et(n,e){const t=Object.assign({},n.params);return{redirect:{pathname:e,from:n.pathname,params:t}}}function Bn(n,e){e.location=T(n);const t=n.chain.map(r=>r.route).indexOf(n.route);return n.chain[t].element=e,e}function be(n,e,t){if(V(n))return n.apply(t,e)}function $t(n,e,t){return r=>{if(r&&(r.cancel||r.redirect))return r;if(t)return be(t[n],e,t)}}function Hn(n,e){if(!Array.isArray(n)&&!Te(n))throw new Error(O(`Incorrect "children" value for the route ${e.path}: expected array or object, but got ${n}`));e.__children=[];const t=$e(n);for(let r=0;r<t.length;r++)Vt(t[r]),e.__children.push(t[r])}function ye(n){if(n&&n.length){const e=n[0].parentNode;for(let t=0;t<n.length;t++)e.removeChild(n[t])}}function we(n,e){const t=e.__effectiveBaseUrl;return t?e.constructor.__createUrl(n.replace(/^\//,""),t).pathname:n}function Qt(n){return n.map(e=>e.path).reduce((e,t)=>t.length?e.replace(/\/$/,"")+"/"+t.replace(/^\//,""):e,"")}class M extends ae{constructor(e,t){const r=document.head.querySelector("base"),o=r&&r.getAttribute("href");super([],Object.assign({baseUrl:o&&ae.__createUrl(o,document.URL).pathname.replace(/[^\/]*$/,"")},t)),this.resolveRoute=s=>this.__resolveRoute(s);const i=M.NavigationTrigger;M.setTriggers.apply(M,Object.keys(i).map(s=>i[s])),this.baseUrl,this.ready,this.ready=Promise.resolve(e),this.location,this.location=T({resolver:this}),this.__lastStartedRenderId=0,this.__navigationEventHandler=this.__onNavigationEvent.bind(this),this.setOutlet(e),this.subscribe(),this.__createdByRouter=new WeakMap,this.__addedByRouter=new WeakMap}__resolveRoute(e){const t=e.route;let r=Promise.resolve();V(t.children)&&(r=r.then(()=>t.children(jn(e))).then(i=>{!De(i)&&!V(t.children)&&(i=t.children),Hn(i,t)}));const o={redirect:i=>Et(e,i),component:i=>{const s=document.createElement(i);return this.__createdByRouter.set(s,!0),s}};return r.then(()=>{if(this.__isLatestRender(e))return be(t.action,[e,o],t)}).then(i=>{if(De(i)&&(i instanceof HTMLElement||i.redirect||i===X))return i;if(R(t.redirect))return o.redirect(t.redirect);if(t.bundle)return mn(t.bundle).then(()=>{},()=>{throw new Error(O(`Bundle not found: ${t.bundle}. Check if the file name is correct`))})}).then(i=>{if(De(i))return i;if(R(t.component))return o.component(t.component)})}setOutlet(e){e&&this.__ensureOutlet(e),this.__outlet=e}getOutlet(){return this.__outlet}setRoutes(e,t=!1){return this.__previousContext=void 0,this.__urlForName=void 0,super.setRoutes(e),t||this.__onNavigationEvent(),this.ready}render(e,t){const r=++this.__lastStartedRenderId,o=Object.assign({search:"",hash:""},R(e)?{pathname:e}:e,{__renderId:r});return this.ready=this.resolve(o).then(i=>this.__fullyResolveChain(i)).then(i=>{if(this.__isLatestRender(i)){const s=this.__previousContext;if(i===s)return this.__updateBrowserHistory(s,!0),this.location;if(this.location=T(i),t&&this.__updateBrowserHistory(i,r===1),re("location-changed",{router:this,location:this.location}),i.__skipAttach)return this.__copyUnchangedElements(i,s),this.__previousContext=i,this.location;this.__addAppearingContent(i,s);const l=this.__animateIfNeeded(i);return this.__runOnAfterEnterCallbacks(i),this.__runOnAfterLeaveCallbacks(i,s),l.then(()=>{if(this.__isLatestRender(i))return this.__removeDisappearingContent(),this.__previousContext=i,this.location})}}).catch(i=>{if(r===this.__lastStartedRenderId)throw t&&this.__updateBrowserHistory(o),ye(this.__outlet&&this.__outlet.children),this.location=T(Object.assign(o,{resolver:this})),re("error",Object.assign({router:this,error:i},o)),i}),this.ready}__fullyResolveChain(e,t=e){return this.__findComponentContextAfterAllRedirects(t).then(r=>{const i=r!==t?r:e,l=we(Qt(r.chain),r.resolver)===r.pathname,a=(c,h=c.route,f)=>c.next(void 0,h,f).then(u=>u===null||u===X?l?c:h.parent!==null?a(c,h.parent,u):u:u);return a(r).then(c=>{if(c===null||c===X)throw jt(i);return c&&c!==X&&c!==r?this.__fullyResolveChain(i,c):this.__amendWithOnBeforeCallbacks(r)})})}__findComponentContextAfterAllRedirects(e){const t=e.result;return t instanceof HTMLElement?(Bn(e,t),Promise.resolve(e)):t.redirect?this.__redirect(t.redirect,e.__redirectCount,e.__renderId).then(r=>this.__findComponentContextAfterAllRedirects(r)):t instanceof Error?Promise.reject(t):Promise.reject(new Error(O(`Invalid route resolution result for path "${e.pathname}". Expected redirect object or HTML element, but got: "${fn(t)}". Double check the action return value for the route.`)))}__amendWithOnBeforeCallbacks(e){return this.__runOnBeforeCallbacks(e).then(t=>t===this.__previousContext||t===e?t:this.__fullyResolveChain(t))}__runOnBeforeCallbacks(e){const t=this.__previousContext||{},r=t.chain||[],o=e.chain;let i=Promise.resolve();const s=()=>({cancel:!0}),l=a=>Et(e,a);if(e.__divergedChainIndex=0,e.__skipAttach=!1,r.length){for(let a=0;a<Math.min(r.length,o.length)&&!(r[a].route!==o[a].route||r[a].path!==o[a].path&&r[a].element!==o[a].element||!this.__isReusableElement(r[a].element,o[a].element));a=++e.__divergedChainIndex);if(e.__skipAttach=o.length===r.length&&e.__divergedChainIndex==o.length&&this.__isReusableElement(e.result,t.result),e.__skipAttach){for(let a=o.length-1;a>=0;a--)i=this.__runOnBeforeLeaveCallbacks(i,e,{prevent:s},r[a]);for(let a=0;a<o.length;a++)i=this.__runOnBeforeEnterCallbacks(i,e,{prevent:s,redirect:l},o[a]),r[a].element.location=T(e,r[a].route)}else for(let a=r.length-1;a>=e.__divergedChainIndex;a--)i=this.__runOnBeforeLeaveCallbacks(i,e,{prevent:s},r[a])}if(!e.__skipAttach)for(let a=0;a<o.length;a++)a<e.__divergedChainIndex?a<r.length&&r[a].element&&(r[a].element.location=T(e,r[a].route)):(i=this.__runOnBeforeEnterCallbacks(i,e,{prevent:s,redirect:l},o[a]),o[a].element&&(o[a].element.location=T(e,o[a].route)));return i.then(a=>{if(a){if(a.cancel)return this.__previousContext.__renderId=e.__renderId,this.__previousContext;if(a.redirect)return this.__redirect(a.redirect,e.__redirectCount,e.__renderId)}return e})}__runOnBeforeLeaveCallbacks(e,t,r,o){const i=T(t);return e.then(s=>{if(this.__isLatestRender(t))return $t("onBeforeLeave",[i,r,this],o.element)(s)}).then(s=>{if(!(s||{}).redirect)return s})}__runOnBeforeEnterCallbacks(e,t,r,o){const i=T(t,o.route);return e.then(s=>{if(this.__isLatestRender(t))return $t("onBeforeEnter",[i,r,this],o.element)(s)})}__isReusableElement(e,t){return e&&t?this.__createdByRouter.get(e)&&this.__createdByRouter.get(t)?e.localName===t.localName:e===t:!1}__isLatestRender(e){return e.__renderId===this.__lastStartedRenderId}__redirect(e,t,r){if(t>Vn)throw new Error(O(`Too many redirects when rendering ${e.from}`));return this.resolve({pathname:this.urlForPath(e.pathname,e.params),redirectFrom:e.from,__redirectCount:(t||0)+1,__renderId:r})}__ensureOutlet(e=this.__outlet){if(!(e instanceof Node))throw new TypeError(O(`Expected router outlet to be a valid DOM Node (but got ${e})`))}__updateBrowserHistory({pathname:e,search:t="",hash:r=""},o){if(window.location.pathname!==e||window.location.search!==t||window.location.hash!==r){const i=o?"replaceState":"pushState";window.history[i](null,document.title,e+t+r),window.dispatchEvent(new PopStateEvent("popstate",{state:"vaadin-router-ignore"}))}}__copyUnchangedElements(e,t){let r=this.__outlet;for(let o=0;o<e.__divergedChainIndex;o++){const i=t&&t.chain[o].element;if(i)if(i.parentNode===r)e.chain[o].element=i,r=i;else break}return r}__addAppearingContent(e,t){this.__ensureOutlet(),this.__removeAppearingContent();const r=this.__copyUnchangedElements(e,t);this.__appearingContent=[],this.__disappearingContent=Array.from(r.children).filter(i=>this.__addedByRouter.get(i)&&i!==e.result);let o=r;for(let i=e.__divergedChainIndex;i<e.chain.length;i++){const s=e.chain[i].element;s&&(o.appendChild(s),this.__addedByRouter.set(s,!0),o===r&&this.__appearingContent.push(s),o=s)}}__removeDisappearingContent(){this.__disappearingContent&&ye(this.__disappearingContent),this.__disappearingContent=null,this.__appearingContent=null}__removeAppearingContent(){this.__disappearingContent&&this.__appearingContent&&(ye(this.__appearingContent),this.__disappearingContent=null,this.__appearingContent=null)}__runOnAfterLeaveCallbacks(e,t){if(t)for(let r=t.chain.length-1;r>=e.__divergedChainIndex&&this.__isLatestRender(e);r--){const o=t.chain[r].element;if(o)try{const i=T(e);be(o.onAfterLeave,[i,{},t.resolver],o)}finally{this.__disappearingContent.indexOf(o)>-1&&ye(o.children)}}}__runOnAfterEnterCallbacks(e){for(let t=e.__divergedChainIndex;t<e.chain.length&&this.__isLatestRender(e);t++){const r=e.chain[t].element||{},o=T(e,e.chain[t].route);be(r.onAfterEnter,[o,{},e.resolver],r)}}__animateIfNeeded(e){const t=(this.__disappearingContent||[])[0],r=(this.__appearingContent||[])[0],o=[],i=e.chain;let s;for(let l=i.length;l>0;l--)if(i[l-1].route.animate){s=i[l-1].route.animate;break}if(t&&r&&s){const l=Te(s)&&s.leave||"leaving",a=Te(s)&&s.enter||"entering";o.push(St(t,l)),o.push(St(r,a))}return Promise.all(o).then(()=>e)}subscribe(){window.addEventListener("vaadin-router-go",this.__navigationEventHandler)}unsubscribe(){window.removeEventListener("vaadin-router-go",this.__navigationEventHandler)}__onNavigationEvent(e){const{pathname:t,search:r,hash:o}=e?e.detail:window.location;R(this.__normalizePathname(t))&&(e&&e.preventDefault&&e.preventDefault(),this.render({pathname:t,search:r,hash:o},!0))}static setTriggers(...e){Un(e)}urlForName(e,t){return this.__urlForName||(this.__urlForName=Mn(this)),we(this.__urlForName(e,t),this)}urlForPath(e,t){return we(M.pathToRegexp.compile(e)(t),this)}static go(e){const{pathname:t,search:r,hash:o}=R(e)?this.__createUrl(e,"http://a"):e;return re("go",{pathname:t,search:r,hash:o})}}const zn=/\/\*[\*!]\s+vaadin-dev-mode:start([\s\S]*)vaadin-dev-mode:end\s+\*\*\//i,Se=window.Vaadin&&window.Vaadin.Flow&&window.Vaadin.Flow.clients;function Wn(){function n(){return!0}return Xt(n)}function qn(){try{return Gn()?!0:Kn()?Se?!Jn():!Wn():!1}catch{return!1}}function Gn(){return localStorage.getItem("vaadin.developmentmode.force")}function Kn(){return["localhost","127.0.0.1"].indexOf(window.location.hostname)>=0}function Jn(){return!!(Se&&Object.keys(Se).map(e=>Se[e]).filter(e=>e.productionMode).length>0)}function Xt(n,e){if(typeof n!="function")return;const t=zn.exec(n.toString());if(t)try{n=new Function(t[1])}catch(r){console.log("vaadin-development-mode-detector: uncommentAndRun() failed",r)}return n(e)}window.Vaadin=window.Vaadin||{};const At=function(n,e){if(window.Vaadin.developmentMode)return Xt(n,e)};window.Vaadin.developmentMode===void 0&&(window.Vaadin.developmentMode=qn());function Qn(){}const Xn=function(){if(typeof At=="function")return At(Qn)};window.Vaadin=window.Vaadin||{};window.Vaadin.registrations=window.Vaadin.registrations||[];window.Vaadin.registrations.push({is:"@vaadin/router",version:"1.7.4"});Xn();M.NavigationTrigger={POPSTATE:_n,CLICK:vn};var Fe,y;(function(n){n.CONNECTED="connected",n.LOADING="loading",n.RECONNECTING="reconnecting",n.CONNECTION_LOST="connection-lost"})(y||(y={}));class Yn{constructor(e){this.stateChangeListeners=new Set,this.loadingCount=0,this.connectionState=e,this.serviceWorkerMessageListener=this.serviceWorkerMessageListener.bind(this),navigator.serviceWorker&&(navigator.serviceWorker.addEventListener("message",this.serviceWorkerMessageListener),navigator.serviceWorker.ready.then(t=>{var r;(r=t.active)===null||r===void 0||r.postMessage({method:"Vaadin.ServiceWorker.isConnectionLost",id:"Vaadin.ServiceWorker.isConnectionLost"})}))}addStateChangeListener(e){this.stateChangeListeners.add(e)}removeStateChangeListener(e){this.stateChangeListeners.delete(e)}loadingStarted(){this.state=y.LOADING,this.loadingCount+=1}loadingFinished(){this.decreaseLoadingCount(y.CONNECTED)}loadingFailed(){this.decreaseLoadingCount(y.CONNECTION_LOST)}decreaseLoadingCount(e){this.loadingCount>0&&(this.loadingCount-=1,this.loadingCount===0&&(this.state=e))}get state(){return this.connectionState}set state(e){if(e!==this.connectionState){const t=this.connectionState;this.connectionState=e,this.loadingCount=0;for(const r of this.stateChangeListeners)r(t,this.connectionState)}}get online(){return this.connectionState===y.CONNECTED||this.connectionState===y.LOADING}get offline(){return!this.online}serviceWorkerMessageListener(e){typeof e.data=="object"&&e.data.id==="Vaadin.ServiceWorker.isConnectionLost"&&(e.data.result===!0&&(this.state=y.CONNECTION_LOST),navigator.serviceWorker.removeEventListener("message",this.serviceWorkerMessageListener))}}const Zn=n=>!!(n==="localhost"||n==="[::1]"||/^127\.\d+\.\d+\.\d+$/u.exec(n)),_e=window;if(!(!((Fe=_e.Vaadin)===null||Fe===void 0)&&Fe.connectionState)){let n;Zn(window.location.hostname)?n=!0:n=navigator.onLine,_e.Vaadin||(_e.Vaadin={}),_e.Vaadin.connectionState=new Yn(n?y.CONNECTED:y.CONNECTION_LOST)}function $(n,e,t,r){var o=arguments.length,i=o<3?e:r===null?r=Object.getOwnPropertyDescriptor(e,t):r,s;if(typeof Reflect=="object"&&typeof Reflect.decorate=="function")i=Reflect.decorate(n,e,t,r);else for(var l=n.length-1;l>=0;l--)(s=n[l])&&(i=(o<3?s(i):o>3?s(e,t,i):s(e,t))||i);return o>3&&i&&Object.defineProperty(e,t,i),i}/**
 * @license
 * Copyright 2019 Google LLC
 * SPDX-License-Identifier: BSD-3-Clause
 */const Ee=globalThis,Xe=Ee.ShadowRoot&&(Ee.ShadyCSS===void 0||Ee.ShadyCSS.nativeShadow)&&"adoptedStyleSheets"in Document.prototype&&"replace"in CSSStyleSheet.prototype,Ye=Symbol(),Ct=new WeakMap;let Ze=class{constructor(e,t,r){if(this._$cssResult$=!0,r!==Ye)throw Error("CSSResult is not constructable. Use `unsafeCSS` or `css` instead.");this.cssText=e,this.t=t}get styleSheet(){let e=this.o;const t=this.t;if(Xe&&e===void 0){const r=t!==void 0&&t.length===1;r&&(e=Ct.get(t)),e===void 0&&((this.o=e=new CSSStyleSheet).replaceSync(this.cssText),r&&Ct.set(t,e))}return e}toString(){return this.cssText}};const Yt=n=>new Ze(typeof n=="string"?n:n+"",void 0,Ye),ee=(n,...e)=>{const t=n.length===1?n[0]:e.reduce((r,o,i)=>r+(s=>{if(s._$cssResult$===!0)return s.cssText;if(typeof s=="number")return s;throw Error("Value passed to 'css' function must be a 'css' function result: "+s+". Use 'unsafeCSS' to pass non-literal values, but take care to ensure page security.")})(o)+n[i+1],n[0]);return new Ze(t,n,Ye)},er=(n,e)=>{if(Xe)n.adoptedStyleSheets=e.map(t=>t instanceof CSSStyleSheet?t:t.styleSheet);else for(const t of e){const r=document.createElement("style"),o=Ee.litNonce;o!==void 0&&r.setAttribute("nonce",o),r.textContent=t.cssText,n.appendChild(r)}},Tt=Xe?n=>n:n=>n instanceof CSSStyleSheet?(e=>{let t="";for(const r of e.cssRules)t+=r.cssText;return Yt(t)})(n):n;/**
 * @license
 * Copyright 2017 Google LLC
 * SPDX-License-Identifier: BSD-3-Clause
 */const{is:tr,defineProperty:nr,getOwnPropertyDescriptor:rr,getOwnPropertyNames:ir,getOwnPropertySymbols:or,getPrototypeOf:sr}=Object,U=globalThis,xt=U.trustedTypes,ar=xt?xt.emptyScript:"",Ve=U.reactiveElementPolyfillSupport,ie=(n,e)=>n,xe={toAttribute(n,e){switch(e){case Boolean:n=n?ar:null;break;case Object:case Array:n=n==null?n:JSON.stringify(n)}return n},fromAttribute(n,e){let t=n;switch(e){case Boolean:t=n!==null;break;case Number:t=n===null?null:Number(n);break;case Object:case Array:try{t=JSON.parse(n)}catch{t=null}}return t}},et=(n,e)=>!tr(n,e),Rt={attribute:!0,type:String,converter:xe,reflect:!1,hasChanged:et};Symbol.metadata??(Symbol.metadata=Symbol("metadata")),U.litPropertyMetadata??(U.litPropertyMetadata=new WeakMap);let Q=class extends HTMLElement{static addInitializer(e){this._$Ei(),(this.l??(this.l=[])).push(e)}static get observedAttributes(){return this.finalize(),this._$Eh&&[...this._$Eh.keys()]}static createProperty(e,t=Rt){if(t.state&&(t.attribute=!1),this._$Ei(),this.elementProperties.set(e,t),!t.noAccessor){const r=Symbol(),o=this.getPropertyDescriptor(e,r,t);o!==void 0&&nr(this.prototype,e,o)}}static getPropertyDescriptor(e,t,r){const{get:o,set:i}=rr(this.prototype,e)??{get(){return this[t]},set(s){this[t]=s}};return{get(){return o==null?void 0:o.call(this)},set(s){const l=o==null?void 0:o.call(this);i.call(this,s),this.requestUpdate(e,l,r)},configurable:!0,enumerable:!0}}static getPropertyOptions(e){return this.elementProperties.get(e)??Rt}static _$Ei(){if(this.hasOwnProperty(ie("elementProperties")))return;const e=sr(this);e.finalize(),e.l!==void 0&&(this.l=[...e.l]),this.elementProperties=new Map(e.elementProperties)}static finalize(){if(this.hasOwnProperty(ie("finalized")))return;if(this.finalized=!0,this._$Ei(),this.hasOwnProperty(ie("properties"))){const t=this.properties,r=[...ir(t),...or(t)];for(const o of r)this.createProperty(o,t[o])}const e=this[Symbol.metadata];if(e!==null){const t=litPropertyMetadata.get(e);if(t!==void 0)for(const[r,o]of t)this.elementProperties.set(r,o)}this._$Eh=new Map;for(const[t,r]of this.elementProperties){const o=this._$Eu(t,r);o!==void 0&&this._$Eh.set(o,t)}this.elementStyles=this.finalizeStyles(this.styles)}static finalizeStyles(e){const t=[];if(Array.isArray(e)){const r=new Set(e.flat(1/0).reverse());for(const o of r)t.unshift(Tt(o))}else e!==void 0&&t.push(Tt(e));return t}static _$Eu(e,t){const r=t.attribute;return r===!1?void 0:typeof r=="string"?r:typeof e=="string"?e.toLowerCase():void 0}constructor(){super(),this._$Ep=void 0,this.isUpdatePending=!1,this.hasUpdated=!1,this._$Em=null,this._$Ev()}_$Ev(){var e;this._$ES=new Promise(t=>this.enableUpdating=t),this._$AL=new Map,this._$E_(),this.requestUpdate(),(e=this.constructor.l)==null||e.forEach(t=>t(this))}addController(e){var t;(this._$EO??(this._$EO=new Set)).add(e),this.renderRoot!==void 0&&this.isConnected&&((t=e.hostConnected)==null||t.call(e))}removeController(e){var t;(t=this._$EO)==null||t.delete(e)}_$E_(){const e=new Map,t=this.constructor.elementProperties;for(const r of t.keys())this.hasOwnProperty(r)&&(e.set(r,this[r]),delete this[r]);e.size>0&&(this._$Ep=e)}createRenderRoot(){const e=this.shadowRoot??this.attachShadow(this.constructor.shadowRootOptions);return er(e,this.constructor.elementStyles),e}connectedCallback(){var e;this.renderRoot??(this.renderRoot=this.createRenderRoot()),this.enableUpdating(!0),(e=this._$EO)==null||e.forEach(t=>{var r;return(r=t.hostConnected)==null?void 0:r.call(t)})}enableUpdating(e){}disconnectedCallback(){var e;(e=this._$EO)==null||e.forEach(t=>{var r;return(r=t.hostDisconnected)==null?void 0:r.call(t)})}attributeChangedCallback(e,t,r){this._$AK(e,r)}_$EC(e,t){var i;const r=this.constructor.elementProperties.get(e),o=this.constructor._$Eu(e,r);if(o!==void 0&&r.reflect===!0){const s=(((i=r.converter)==null?void 0:i.toAttribute)!==void 0?r.converter:xe).toAttribute(t,r.type);this._$Em=e,s==null?this.removeAttribute(o):this.setAttribute(o,s),this._$Em=null}}_$AK(e,t){var i;const r=this.constructor,o=r._$Eh.get(e);if(o!==void 0&&this._$Em!==o){const s=r.getPropertyOptions(o),l=typeof s.converter=="function"?{fromAttribute:s.converter}:((i=s.converter)==null?void 0:i.fromAttribute)!==void 0?s.converter:xe;this._$Em=o,this[o]=l.fromAttribute(t,s.type),this._$Em=null}}requestUpdate(e,t,r){if(e!==void 0){if(r??(r=this.constructor.getPropertyOptions(e)),!(r.hasChanged??et)(this[e],t))return;this.P(e,t,r)}this.isUpdatePending===!1&&(this._$ES=this._$ET())}P(e,t,r){this._$AL.has(e)||this._$AL.set(e,t),r.reflect===!0&&this._$Em!==e&&(this._$Ej??(this._$Ej=new Set)).add(e)}async _$ET(){this.isUpdatePending=!0;try{await this._$ES}catch(t){Promise.reject(t)}const e=this.scheduleUpdate();return e!=null&&await e,!this.isUpdatePending}scheduleUpdate(){return this.performUpdate()}performUpdate(){var r;if(!this.isUpdatePending)return;if(!this.hasUpdated){if(this.renderRoot??(this.renderRoot=this.createRenderRoot()),this._$Ep){for(const[i,s]of this._$Ep)this[i]=s;this._$Ep=void 0}const o=this.constructor.elementProperties;if(o.size>0)for(const[i,s]of o)s.wrapped!==!0||this._$AL.has(i)||this[i]===void 0||this.P(i,this[i],s)}let e=!1;const t=this._$AL;try{e=this.shouldUpdate(t),e?(this.willUpdate(t),(r=this._$EO)==null||r.forEach(o=>{var i;return(i=o.hostUpdate)==null?void 0:i.call(o)}),this.update(t)):this._$EU()}catch(o){throw e=!1,this._$EU(),o}e&&this._$AE(t)}willUpdate(e){}_$AE(e){var t;(t=this._$EO)==null||t.forEach(r=>{var o;return(o=r.hostUpdated)==null?void 0:o.call(r)}),this.hasUpdated||(this.hasUpdated=!0,this.firstUpdated(e)),this.updated(e)}_$EU(){this._$AL=new Map,this.isUpdatePending=!1}get updateComplete(){return this.getUpdateComplete()}getUpdateComplete(){return this._$ES}shouldUpdate(e){return!0}update(e){this._$Ej&&(this._$Ej=this._$Ej.forEach(t=>this._$EC(t,this[t]))),this._$EU()}updated(e){}firstUpdated(e){}};Q.elementStyles=[],Q.shadowRootOptions={mode:"open"},Q[ie("elementProperties")]=new Map,Q[ie("finalized")]=new Map,Ve==null||Ve({ReactiveElement:Q}),(U.reactiveElementVersions??(U.reactiveElementVersions=[])).push("2.0.4");/**
 * @license
 * Copyright 2017 Google LLC
 * SPDX-License-Identifier: BSD-3-Clause
 */const oe=globalThis,Re=oe.trustedTypes,Ot=Re?Re.createPolicy("lit-html",{createHTML:n=>n}):void 0,Zt="$lit$",k=`lit$${(Math.random()+"").slice(9)}$`,en="?"+k,lr=`<${en}>`,j=document,le=()=>j.createComment(""),ce=n=>n===null||typeof n!="object"&&typeof n!="function",tn=Array.isArray,cr=n=>tn(n)||typeof(n==null?void 0:n[Symbol.iterator])=="function",je=`[ 	
\f\r]`,te=/<(?:(!--|\/[^a-zA-Z])|(\/?[a-zA-Z][^>\s]*)|(\/?$))/g,Pt=/-->/g,Lt=/>/g,D=RegExp(`>|${je}(?:([^\\s"'>=/]+)(${je}*=${je}*(?:[^ 	
\f\r"'\`<>=]|("|')|))|$)`,"g"),It=/'/g,Nt=/"/g,nn=/^(?:script|style|textarea|title)$/i,rn=n=>(e,...t)=>({_$litType$:n,strings:e,values:t}),dr=rn(1),ni=rn(2),B=Symbol.for("lit-noChange"),_=Symbol.for("lit-nothing"),kt=new WeakMap,F=j.createTreeWalker(j,129);function on(n,e){if(!Array.isArray(n)||!n.hasOwnProperty("raw"))throw Error("invalid template strings array");return Ot!==void 0?Ot.createHTML(e):e}const hr=(n,e)=>{const t=n.length-1,r=[];let o,i=e===2?"<svg>":"",s=te;for(let l=0;l<t;l++){const a=n[l];let c,h,f=-1,u=0;for(;u<a.length&&(s.lastIndex=u,h=s.exec(a),h!==null);)u=s.lastIndex,s===te?h[1]==="!--"?s=Pt:h[1]!==void 0?s=Lt:h[2]!==void 0?(nn.test(h[2])&&(o=RegExp("</"+h[2],"g")),s=D):h[3]!==void 0&&(s=D):s===D?h[0]===">"?(s=o??te,f=-1):h[1]===void 0?f=-2:(f=s.lastIndex-h[2].length,c=h[1],s=h[3]===void 0?D:h[3]==='"'?Nt:It):s===Nt||s===It?s=D:s===Pt||s===Lt?s=te:(s=D,o=void 0);const m=s===D&&n[l+1].startsWith("/>")?" ":"";i+=s===te?a+lr:f>=0?(r.push(c),a.slice(0,f)+Zt+a.slice(f)+k+m):a+k+(f===-2?l:m)}return[on(n,i+(n[t]||"<?>")+(e===2?"</svg>":"")),r]};class de{constructor({strings:e,_$litType$:t},r){let o;this.parts=[];let i=0,s=0;const l=e.length-1,a=this.parts,[c,h]=hr(e,t);if(this.el=de.createElement(c,r),F.currentNode=this.el.content,t===2){const f=this.el.content.firstChild;f.replaceWith(...f.childNodes)}for(;(o=F.nextNode())!==null&&a.length<l;){if(o.nodeType===1){if(o.hasAttributes())for(const f of o.getAttributeNames())if(f.endsWith(Zt)){const u=h[s++],m=o.getAttribute(f).split(k),L=/([.?@])?(.*)/.exec(u);a.push({type:1,index:i,name:L[2],strings:m,ctor:L[1]==="."?pr:L[1]==="?"?fr:L[1]==="@"?mr:Pe}),o.removeAttribute(f)}else f.startsWith(k)&&(a.push({type:6,index:i}),o.removeAttribute(f));if(nn.test(o.tagName)){const f=o.textContent.split(k),u=f.length-1;if(u>0){o.textContent=Re?Re.emptyScript:"";for(let m=0;m<u;m++)o.append(f[m],le()),F.nextNode(),a.push({type:2,index:++i});o.append(f[u],le())}}}else if(o.nodeType===8)if(o.data===en)a.push({type:2,index:i});else{let f=-1;for(;(f=o.data.indexOf(k,f+1))!==-1;)a.push({type:7,index:i}),f+=k.length-1}i++}}static createElement(e,t){const r=j.createElement("template");return r.innerHTML=e,r}}function Y(n,e,t=n,r){var s,l;if(e===B)return e;let o=r!==void 0?(s=t._$Co)==null?void 0:s[r]:t._$Cl;const i=ce(e)?void 0:e._$litDirective$;return(o==null?void 0:o.constructor)!==i&&((l=o==null?void 0:o._$AO)==null||l.call(o,!1),i===void 0?o=void 0:(o=new i(n),o._$AT(n,t,r)),r!==void 0?(t._$Co??(t._$Co=[]))[r]=o:t._$Cl=o),o!==void 0&&(e=Y(n,o._$AS(n,e.values),o,r)),e}class ur{constructor(e,t){this._$AV=[],this._$AN=void 0,this._$AD=e,this._$AM=t}get parentNode(){return this._$AM.parentNode}get _$AU(){return this._$AM._$AU}u(e){const{el:{content:t},parts:r}=this._$AD,o=((e==null?void 0:e.creationScope)??j).importNode(t,!0);F.currentNode=o;let i=F.nextNode(),s=0,l=0,a=r[0];for(;a!==void 0;){if(s===a.index){let c;a.type===2?c=new he(i,i.nextSibling,this,e):a.type===1?c=new a.ctor(i,a.name,a.strings,this,e):a.type===6&&(c=new gr(i,this,e)),this._$AV.push(c),a=r[++l]}s!==(a==null?void 0:a.index)&&(i=F.nextNode(),s++)}return F.currentNode=j,o}p(e){let t=0;for(const r of this._$AV)r!==void 0&&(r.strings!==void 0?(r._$AI(e,r,t),t+=r.strings.length-2):r._$AI(e[t])),t++}}class he{get _$AU(){var e;return((e=this._$AM)==null?void 0:e._$AU)??this._$Cv}constructor(e,t,r,o){this.type=2,this._$AH=_,this._$AN=void 0,this._$AA=e,this._$AB=t,this._$AM=r,this.options=o,this._$Cv=(o==null?void 0:o.isConnected)??!0}get parentNode(){let e=this._$AA.parentNode;const t=this._$AM;return t!==void 0&&(e==null?void 0:e.nodeType)===11&&(e=t.parentNode),e}get startNode(){return this._$AA}get endNode(){return this._$AB}_$AI(e,t=this){e=Y(this,e,t),ce(e)?e===_||e==null||e===""?(this._$AH!==_&&this._$AR(),this._$AH=_):e!==this._$AH&&e!==B&&this._(e):e._$litType$!==void 0?this.$(e):e.nodeType!==void 0?this.T(e):cr(e)?this.k(e):this._(e)}S(e){return this._$AA.parentNode.insertBefore(e,this._$AB)}T(e){this._$AH!==e&&(this._$AR(),this._$AH=this.S(e))}_(e){this._$AH!==_&&ce(this._$AH)?this._$AA.nextSibling.data=e:this.T(j.createTextNode(e)),this._$AH=e}$(e){var i;const{values:t,_$litType$:r}=e,o=typeof r=="number"?this._$AC(e):(r.el===void 0&&(r.el=de.createElement(on(r.h,r.h[0]),this.options)),r);if(((i=this._$AH)==null?void 0:i._$AD)===o)this._$AH.p(t);else{const s=new ur(o,this),l=s.u(this.options);s.p(t),this.T(l),this._$AH=s}}_$AC(e){let t=kt.get(e.strings);return t===void 0&&kt.set(e.strings,t=new de(e)),t}k(e){tn(this._$AH)||(this._$AH=[],this._$AR());const t=this._$AH;let r,o=0;for(const i of e)o===t.length?t.push(r=new he(this.S(le()),this.S(le()),this,this.options)):r=t[o],r._$AI(i),o++;o<t.length&&(this._$AR(r&&r._$AB.nextSibling,o),t.length=o)}_$AR(e=this._$AA.nextSibling,t){var r;for((r=this._$AP)==null?void 0:r.call(this,!1,!0,t);e&&e!==this._$AB;){const o=e.nextSibling;e.remove(),e=o}}setConnected(e){var t;this._$AM===void 0&&(this._$Cv=e,(t=this._$AP)==null||t.call(this,e))}}class Pe{get tagName(){return this.element.tagName}get _$AU(){return this._$AM._$AU}constructor(e,t,r,o,i){this.type=1,this._$AH=_,this._$AN=void 0,this.element=e,this.name=t,this._$AM=o,this.options=i,r.length>2||r[0]!==""||r[1]!==""?(this._$AH=Array(r.length-1).fill(new String),this.strings=r):this._$AH=_}_$AI(e,t=this,r,o){const i=this.strings;let s=!1;if(i===void 0)e=Y(this,e,t,0),s=!ce(e)||e!==this._$AH&&e!==B,s&&(this._$AH=e);else{const l=e;let a,c;for(e=i[0],a=0;a<i.length-1;a++)c=Y(this,l[r+a],t,a),c===B&&(c=this._$AH[a]),s||(s=!ce(c)||c!==this._$AH[a]),c===_?e=_:e!==_&&(e+=(c??"")+i[a+1]),this._$AH[a]=c}s&&!o&&this.j(e)}j(e){e===_?this.element.removeAttribute(this.name):this.element.setAttribute(this.name,e??"")}}class pr extends Pe{constructor(){super(...arguments),this.type=3}j(e){this.element[this.name]=e===_?void 0:e}}class fr extends Pe{constructor(){super(...arguments),this.type=4}j(e){this.element.toggleAttribute(this.name,!!e&&e!==_)}}class mr extends Pe{constructor(e,t,r,o,i){super(e,t,r,o,i),this.type=5}_$AI(e,t=this){if((e=Y(this,e,t,0)??_)===B)return;const r=this._$AH,o=e===_&&r!==_||e.capture!==r.capture||e.once!==r.once||e.passive!==r.passive,i=e!==_&&(r===_||o);o&&this.element.removeEventListener(this.name,this,r),i&&this.element.addEventListener(this.name,this,e),this._$AH=e}handleEvent(e){var t;typeof this._$AH=="function"?this._$AH.call(((t=this.options)==null?void 0:t.host)??this.element,e):this._$AH.handleEvent(e)}}class gr{constructor(e,t,r){this.element=e,this.type=6,this._$AN=void 0,this._$AM=t,this.options=r}get _$AU(){return this._$AM._$AU}_$AI(e){Y(this,e)}}const Be=oe.litHtmlPolyfillSupport;Be==null||Be(de,he),(oe.litHtmlVersions??(oe.litHtmlVersions=[])).push("3.1.2");const vr=(n,e,t)=>{const r=(t==null?void 0:t.renderBefore)??e;let o=r._$litPart$;if(o===void 0){const i=(t==null?void 0:t.renderBefore)??null;r._$litPart$=o=new he(e.insertBefore(le(),i),i,void 0,t??{})}return o._$AI(n),o};/**
 * @license
 * Copyright 2017 Google LLC
 * SPDX-License-Identifier: BSD-3-Clause
 */class se extends Q{constructor(){super(...arguments),this.renderOptions={host:this},this._$Do=void 0}createRenderRoot(){var t;const e=super.createRenderRoot();return(t=this.renderOptions).renderBefore??(t.renderBefore=e.firstChild),e}update(e){const t=this.render();this.hasUpdated||(this.renderOptions.isConnected=this.isConnected),super.update(e),this._$Do=vr(t,this.renderRoot,this.renderOptions)}connectedCallback(){var e;super.connectedCallback(),(e=this._$Do)==null||e.setConnected(!0)}disconnectedCallback(){var e;super.disconnectedCallback(),(e=this._$Do)==null||e.setConnected(!1)}render(){return B}}var Ft;se._$litElement$=!0,se.finalized=!0,(Ft=globalThis.litElementHydrateSupport)==null||Ft.call(globalThis,{LitElement:se});const He=globalThis.litElementPolyfillSupport;He==null||He({LitElement:se});(globalThis.litElementVersions??(globalThis.litElementVersions=[])).push("4.0.4");/**
 * @license
 * Copyright 2017 Google LLC
 * SPDX-License-Identifier: BSD-3-Clause
 */const yr={attribute:!0,type:String,converter:xe,reflect:!1,hasChanged:et},_r=(n=yr,e,t)=>{const{kind:r,metadata:o}=t;let i=globalThis.litPropertyMetadata.get(o);if(i===void 0&&globalThis.litPropertyMetadata.set(o,i=new Map),i.set(t.name,n),r==="accessor"){const{name:s}=t;return{set(l){const a=e.get.call(this);e.set.call(this,l),this.requestUpdate(s,a,n)},init(l){return l!==void 0&&this.P(s,void 0,n),l}}}if(r==="setter"){const{name:s}=t;return function(l){const a=this[s];e.call(this,l),this.requestUpdate(s,a,n)}}throw Error("Unsupported decorator location: "+r)};function A(n){return(e,t)=>typeof t=="object"?_r(n,e,t):((r,o,i)=>{const s=o.hasOwnProperty(i);return o.constructor.createProperty(i,s?{...r,wrapped:!0}:r),s?Object.getOwnPropertyDescriptor(o,i):void 0})(n,e,t)}/**
 * @license
 * Copyright 2017 Google LLC
 * SPDX-License-Identifier: BSD-3-Clause
 */const br={ATTRIBUTE:1,CHILD:2,PROPERTY:3,BOOLEAN_ATTRIBUTE:4,EVENT:5,ELEMENT:6},wr=n=>(...e)=>({_$litDirective$:n,values:e});class Sr{constructor(e){}get _$AU(){return this._$AM._$AU}_$AT(e,t,r){this._$Ct=e,this._$AM=t,this._$Ci=r}_$AS(e,t){return this.update(e,t)}update(e,t){return this.render(...t)}}/**
 * @license
 * Copyright 2018 Google LLC
 * SPDX-License-Identifier: BSD-3-Clause
 */const Er=wr(class extends Sr{constructor(n){var e;if(super(n),n.type!==br.ATTRIBUTE||n.name!=="class"||((e=n.strings)==null?void 0:e.length)>2)throw Error("`classMap()` can only be used in the `class` attribute and must be the only part in the attribute.")}render(n){return" "+Object.keys(n).filter(e=>n[e]).join(" ")+" "}update(n,[e]){var r,o;if(this.st===void 0){this.st=new Set,n.strings!==void 0&&(this.nt=new Set(n.strings.join(" ").split(/\s/).filter(i=>i!=="")));for(const i in e)e[i]&&!((r=this.nt)!=null&&r.has(i))&&this.st.add(i);return this.render(e)}const t=n.element.classList;for(const i of this.st)i in e||(t.remove(i),this.st.delete(i));for(const i in e){const s=!!e[i];s===this.st.has(i)||(o=this.nt)!=null&&o.has(i)||(s?(t.add(i),this.st.add(i)):(t.remove(i),this.st.delete(i)))}return B}}),ze="css-loading-indicator";var x;(function(n){n.IDLE="",n.FIRST="first",n.SECOND="second",n.THIRD="third"})(x||(x={}));class b extends se{static create(){var e,t;const r=window;return!((e=r.Vaadin)===null||e===void 0)&&e.connectionIndicator||(r.Vaadin||(r.Vaadin={}),r.Vaadin.connectionIndicator=document.createElement("vaadin-connection-indicator"),document.body.appendChild(r.Vaadin.connectionIndicator)),(t=r.Vaadin)===null||t===void 0?void 0:t.connectionIndicator}constructor(){super(),this.firstDelay=450,this.secondDelay=1500,this.thirdDelay=5e3,this.expandedDuration=2e3,this.onlineText="Online",this.offlineText="Connection lost",this.reconnectingText="Connection lost, trying to reconnect...",this.offline=!1,this.reconnecting=!1,this.expanded=!1,this.loading=!1,this.loadingBarState=x.IDLE,this.applyDefaultThemeState=!0,this.firstTimeout=0,this.secondTimeout=0,this.thirdTimeout=0,this.expandedTimeout=0,this.lastMessageState=y.CONNECTED,this.connectionStateListener=()=>{this.expanded=this.updateConnectionState(),this.expandedTimeout=this.timeoutFor(this.expandedTimeout,this.expanded,()=>{this.expanded=!1},this.expandedDuration)}}render(){return dr`
      <div class="v-loading-indicator ${this.loadingBarState}" style=${this.getLoadingBarStyle()}></div>

      <div
        class="v-status-message ${Er({active:this.reconnecting})}"
      >
        <span class="text"> ${this.renderMessage()} </span>
      </div>
    `}connectedCallback(){var e;super.connectedCallback();const t=window;!((e=t.Vaadin)===null||e===void 0)&&e.connectionState&&(this.connectionStateStore=t.Vaadin.connectionState,this.connectionStateStore.addStateChangeListener(this.connectionStateListener),this.updateConnectionState()),this.updateTheme()}disconnectedCallback(){super.disconnectedCallback(),this.connectionStateStore&&this.connectionStateStore.removeStateChangeListener(this.connectionStateListener),this.updateTheme()}get applyDefaultTheme(){return this.applyDefaultThemeState}set applyDefaultTheme(e){e!==this.applyDefaultThemeState&&(this.applyDefaultThemeState=e,this.updateTheme())}createRenderRoot(){return this}updateConnectionState(){var e;const t=(e=this.connectionStateStore)===null||e===void 0?void 0:e.state;return this.offline=t===y.CONNECTION_LOST,this.reconnecting=t===y.RECONNECTING,this.updateLoading(t===y.LOADING),this.loading?!1:t!==this.lastMessageState?(this.lastMessageState=t,!0):!1}updateLoading(e){this.loading=e,this.loadingBarState=x.IDLE,this.firstTimeout=this.timeoutFor(this.firstTimeout,e,()=>{this.loadingBarState=x.FIRST},this.firstDelay),this.secondTimeout=this.timeoutFor(this.secondTimeout,e,()=>{this.loadingBarState=x.SECOND},this.secondDelay),this.thirdTimeout=this.timeoutFor(this.thirdTimeout,e,()=>{this.loadingBarState=x.THIRD},this.thirdDelay)}renderMessage(){return this.reconnecting?this.reconnectingText:this.offline?this.offlineText:this.onlineText}updateTheme(){if(this.applyDefaultThemeState&&this.isConnected){if(!document.getElementById(ze)){const e=document.createElement("style");e.id=ze,e.textContent=this.getDefaultStyle(),document.head.appendChild(e)}}else{const e=document.getElementById(ze);e&&document.head.removeChild(e)}}getDefaultStyle(){return`
      @keyframes v-progress-start {
        0% {
          width: 0%;
        }
        100% {
          width: 50%;
        }
      }
      @keyframes v-progress-delay {
        0% {
          width: 50%;
        }
        100% {
          width: 90%;
        }
      }
      @keyframes v-progress-wait {
        0% {
          width: 90%;
          height: 4px;
        }
        3% {
          width: 91%;
          height: 7px;
        }
        100% {
          width: 96%;
          height: 7px;
        }
      }
      @keyframes v-progress-wait-pulse {
        0% {
          opacity: 1;
        }
        50% {
          opacity: 0.1;
        }
        100% {
          opacity: 1;
        }
      }
      .v-loading-indicator,
      .v-status-message {
        position: fixed;
        z-index: 251;
        left: 0;
        right: auto;
        top: 0;
        background-color: var(--lumo-primary-color, var(--material-primary-color, blue));
        transition: none;
      }
      .v-loading-indicator {
        width: 50%;
        height: 4px;
        opacity: 1;
        pointer-events: none;
        animation: v-progress-start 1000ms 200ms both;
      }
      .v-loading-indicator[style*='none'] {
        display: block !important;
        width: 100%;
        opacity: 0;
        animation: none;
        transition: opacity 500ms 300ms, width 300ms;
      }
      .v-loading-indicator.second {
        width: 90%;
        animation: v-progress-delay 3.8s forwards;
      }
      .v-loading-indicator.third {
        width: 96%;
        animation: v-progress-wait 5s forwards, v-progress-wait-pulse 1s 4s infinite backwards;
      }

      vaadin-connection-indicator[offline] .v-loading-indicator,
      vaadin-connection-indicator[reconnecting] .v-loading-indicator {
        display: none;
      }

      .v-status-message {
        opacity: 0;
        width: 100%;
        max-height: var(--status-height-collapsed, 8px);
        overflow: hidden;
        background-color: var(--status-bg-color-online, var(--lumo-primary-color, var(--material-primary-color, blue)));
        color: var(
          --status-text-color-online,
          var(--lumo-primary-contrast-color, var(--material-primary-contrast-color, #fff))
        );
        font-size: 0.75rem;
        font-weight: 600;
        line-height: 1;
        transition: all 0.5s;
        padding: 0 0.5em;
      }

      vaadin-connection-indicator[offline] .v-status-message,
      vaadin-connection-indicator[reconnecting] .v-status-message {
        opacity: 1;
        background-color: var(--status-bg-color-offline, var(--lumo-shade, #333));
        color: var(
          --status-text-color-offline,
          var(--lumo-primary-contrast-color, var(--material-primary-contrast-color, #fff))
        );
        background-image: repeating-linear-gradient(
          45deg,
          rgba(255, 255, 255, 0),
          rgba(255, 255, 255, 0) 10px,
          rgba(255, 255, 255, 0.1) 10px,
          rgba(255, 255, 255, 0.1) 20px
        );
      }

      vaadin-connection-indicator[reconnecting] .v-status-message {
        animation: show-reconnecting-status 2s;
      }

      vaadin-connection-indicator[offline] .v-status-message:hover,
      vaadin-connection-indicator[reconnecting] .v-status-message:hover,
      vaadin-connection-indicator[expanded] .v-status-message {
        max-height: var(--status-height, 1.75rem);
      }

      vaadin-connection-indicator[expanded] .v-status-message {
        opacity: 1;
      }

      .v-status-message span {
        display: flex;
        align-items: center;
        justify-content: center;
        height: var(--status-height, 1.75rem);
      }

      vaadin-connection-indicator[reconnecting] .v-status-message span::before {
        content: '';
        width: 1em;
        height: 1em;
        border-top: 2px solid
          var(--status-spinner-color, var(--lumo-primary-color, var(--material-primary-color, blue)));
        border-left: 2px solid
          var(--status-spinner-color, var(--lumo-primary-color, var(--material-primary-color, blue)));
        border-right: 2px solid transparent;
        border-bottom: 2px solid transparent;
        border-radius: 50%;
        box-sizing: border-box;
        animation: v-spin 0.4s linear infinite;
        margin: 0 0.5em;
      }

      @keyframes v-spin {
        100% {
          transform: rotate(360deg);
        }
      }
    `}getLoadingBarStyle(){switch(this.loadingBarState){case x.IDLE:return"display: none";case x.FIRST:case x.SECOND:case x.THIRD:return"display: block";default:return""}}timeoutFor(e,t,r,o){return e!==0&&window.clearTimeout(e),t?window.setTimeout(r,o):0}static get instance(){return b.create()}}$([A({type:Number})],b.prototype,"firstDelay",void 0);$([A({type:Number})],b.prototype,"secondDelay",void 0);$([A({type:Number})],b.prototype,"thirdDelay",void 0);$([A({type:Number})],b.prototype,"expandedDuration",void 0);$([A({type:String})],b.prototype,"onlineText",void 0);$([A({type:String})],b.prototype,"offlineText",void 0);$([A({type:String})],b.prototype,"reconnectingText",void 0);$([A({type:Boolean,reflect:!0})],b.prototype,"offline",void 0);$([A({type:Boolean,reflect:!0})],b.prototype,"reconnecting",void 0);$([A({type:Boolean,reflect:!0})],b.prototype,"expanded",void 0);$([A({type:Boolean,reflect:!0})],b.prototype,"loading",void 0);$([A({type:String})],b.prototype,"loadingBarState",void 0);$([A({type:Boolean})],b.prototype,"applyDefaultTheme",null);customElements.get("vaadin-connection-indicator")===void 0&&customElements.define("vaadin-connection-indicator",b);b.instance;var Mt;const Oe=window;Oe.Vaadin||(Oe.Vaadin={});(Mt=Oe.Vaadin).registrations||(Mt.registrations=[]);Oe.Vaadin.registrations.push({is:"@vaadin/common-frontend",version:"0.0.18"});class Ut extends Error{}const ne=window.document.body,v=window;class $r{constructor(e){this.response=void 0,this.pathname="",this.isActive=!1,this.baseRegex=/^\//,this.navigation="",ne.$=ne.$||[],this.config=e||{},v.Vaadin=v.Vaadin||{},v.Vaadin.Flow=v.Vaadin.Flow||{},v.Vaadin.Flow.clients={TypeScript:{isActive:()=>this.isActive}};const t=document.head.querySelector("base");this.baseRegex=new RegExp(`^${(document.baseURI||t&&t.href||"/").replace(/^https?:\/\/[^/]+/i,"")}`),this.appShellTitle=document.title,this.addConnectionIndicator()}get serverSideRoutes(){return[{path:"(.*)",action:this.action}]}loadingStarted(){this.isActive=!0,v.Vaadin.connectionState.loadingStarted()}loadingFinished(){this.isActive=!1,v.Vaadin.connectionState.loadingFinished(),!v.Vaadin.listener&&(v.Vaadin.listener={},document.addEventListener("click",e=>{e.target&&(e.target.hasAttribute("router-link")?this.navigation="link":e.composedPath().some(t=>t.nodeName==="A")&&(this.navigation="client"))},{capture:!0}))}get action(){return async e=>{if(this.pathname=e.pathname,v.Vaadin.connectionState.online)try{await this.flowInit()}catch(t){if(t instanceof Ut)return v.Vaadin.connectionState.state=y.CONNECTION_LOST,this.offlineStubAction();throw t}else return this.offlineStubAction();return this.container.onBeforeEnter=(t,r)=>this.flowNavigate(t,r),this.container.onBeforeLeave=(t,r)=>this.flowLeave(t,r),this.container}}async flowLeave(e,t){const{connectionState:r}=v.Vaadin;return this.pathname===e.pathname||!this.isFlowClientLoaded()||r.offline?Promise.resolve({}):new Promise(o=>{this.loadingStarted(),this.container.serverConnected=i=>{o(t&&i?t.prevent():{}),this.loadingFinished()},ne.$server.leaveNavigation(this.getFlowRoutePath(e),this.getFlowRouteQuery(e))})}async flowNavigate(e,t){return this.response?new Promise(r=>{this.loadingStarted(),this.container.serverConnected=(o,i)=>{t&&o?r(t.prevent()):t&&t.redirect&&i?r(t.redirect(i.pathname)):(this.container.style.display="",r(this.container)),this.loadingFinished()},this.container.serverPaused=()=>{this.loadingFinished()},ne.$server.connectClient(this.getFlowRoutePath(e),this.getFlowRouteQuery(e),this.appShellTitle,history.state,this.navigation),this.navigation="history"}):Promise.resolve(this.container)}getFlowRoutePath(e){return decodeURIComponent(e.pathname).replace(this.baseRegex,"")}getFlowRouteQuery(e){return e.search&&e.search.substring(1)||""}async flowInit(){if(!this.isFlowClientLoaded()){this.loadingStarted(),this.response=await this.flowInitUi();const{pushScript:e,appConfig:t}=this.response;typeof e=="string"&&await this.loadScript(e);const{appId:r}=t;await(await qe(()=>import("./FlowBootstrap-CHUuW4WK.js"),__vite__mapDeps([]),import.meta.url)).init(this.response),typeof this.config.imports=="function"&&(this.injectAppIdScript(r),await this.config.imports());const i=`flow-container-${r.toLowerCase()}`,s=document.querySelector(i);s?this.container=s:(this.container=document.createElement(i),this.container.id=r),ne.$[r]=this.container;const l=await qe(()=>import("./FlowClient-BZ2ixoyw.js"),__vite__mapDeps([]),import.meta.url);await this.flowInitClient(l),this.loadingFinished()}return this.container&&!this.container.isConnected&&(this.container.style.display="none",document.body.appendChild(this.container)),this.response}async loadScript(e){return new Promise((t,r)=>{const o=document.createElement("script");o.onload=()=>t(),o.onerror=r,o.src=e,document.body.appendChild(o)})}injectAppIdScript(e){const t=e.substring(0,e.lastIndexOf("-")),r=document.createElement("script");r.type="module",r.setAttribute("data-app-id",t),document.body.append(r)}async flowInitClient(e){return e.init(),new Promise(t=>{const r=setInterval(()=>{Object.keys(v.Vaadin.Flow.clients).filter(i=>i!=="TypeScript").reduce((i,s)=>i||v.Vaadin.Flow.clients[s].isActive(),!1)||(clearInterval(r),t())},5)})}async flowInitUi(){const e=v.Vaadin&&v.Vaadin.TypeScript&&v.Vaadin.TypeScript.initial;return e?(v.Vaadin.TypeScript.initial=void 0,Promise.resolve(e)):new Promise((t,r)=>{const i=new XMLHttpRequest,s=`?v-r=init&location=${encodeURIComponent(this.getFlowRoutePath(location))}&query=${encodeURIComponent(this.getFlowRouteQuery(location))}`;i.open("GET",s),i.onerror=()=>r(new Ut(`Invalid server response when initializing Flow UI.
        ${i.status}
        ${i.responseText}`)),i.onload=()=>{const l=i.getResponseHeader("content-type");l&&l.indexOf("application/json")!==-1?t(JSON.parse(i.responseText)):i.onerror()},i.send()})}addConnectionIndicator(){b.create(),v.addEventListener("online",()=>{if(!this.isFlowClientLoaded()){v.Vaadin.connectionState.state=y.RECONNECTING;const e=new XMLHttpRequest;e.open("HEAD","sw.js"),e.onload=()=>{v.Vaadin.connectionState.state=y.CONNECTED},e.onerror=()=>{v.Vaadin.connectionState.state=y.CONNECTION_LOST},setTimeout(()=>e.send(),50)}}),v.addEventListener("offline",()=>{this.isFlowClientLoaded()||(v.Vaadin.connectionState.state=y.CONNECTION_LOST)})}async offlineStubAction(){const e=document.createElement("iframe");e.setAttribute("src","./offline-stub.html"),e.setAttribute("style","width: 100%; height: 100%; border: 0"),this.response=void 0;let r;const o=()=>{r!==void 0&&(v.Vaadin.connectionState.removeStateChangeListener(r),r=void 0)};return e.onBeforeEnter=(i,s,l)=>{r=()=>{v.Vaadin.connectionState.online&&(o(),l.render(i,!1))},v.Vaadin.connectionState.addStateChangeListener(r)},e.onBeforeLeave=(i,s,l)=>{o()},e}isFlowClientLoaded(){return this.response!==void 0}}const{serverSideRoutes:Ar}=new $r({imports:()=>qe(()=>import("./chunk-e13c37f1b1adde3f8d6ab81ef414efbba0b3862e264bacff37ccc53089e68ec7-Q0KIV-_v.js").then(n=>n.j),__vite__mapDeps([]),import.meta.url)}),Cr=[...Ar],Tr=new M(document.querySelector("#outlet"));Tr.setRoutes(Cr);(function(){if(typeof document>"u"||"adoptedStyleSheets"in document)return;var n="ShadyCSS"in window&&!ShadyCSS.nativeShadow,e=document.implementation.createHTMLDocument(""),t=new WeakMap,r=typeof DOMException=="object"?Error:DOMException,o=Object.defineProperty,i=Array.prototype.forEach,s=/@import.+?;?$/gm;function l(d){var p=d.replace(s,"");return p!==d&&console.warn("@import rules are not allowed here. See https://github.com/WICG/construct-stylesheets/issues/119#issuecomment-588352418"),p.trim()}function a(d){return"isConnected"in d?d.isConnected:document.contains(d)}function c(d){return d.filter(function(p,g){return d.indexOf(p)===g})}function h(d,p){return d.filter(function(g){return p.indexOf(g)===-1})}function f(d){d.parentNode.removeChild(d)}function u(d){return d.shadowRoot||t.get(d)}var m=["addRule","deleteRule","insertRule","removeRule"],L=CSSStyleSheet,H=L.prototype;H.replace=function(){return Promise.reject(new r("Can't call replace on non-constructed CSSStyleSheets."))},H.replaceSync=function(){throw new r("Failed to execute 'replaceSync' on 'CSSStyleSheet': Can't call replaceSync on non-constructed CSSStyleSheets.")};function ue(d){return typeof d=="object"?q.isPrototypeOf(d)||H.isPrototypeOf(d):!1}function Ie(d){return typeof d=="object"?H.isPrototypeOf(d):!1}var C=new WeakMap,P=new WeakMap,z=new WeakMap,W=new WeakMap;function Ne(d,p){var g=document.createElement("style");return z.get(d).set(p,g),P.get(d).push(p),g}function I(d,p){return z.get(d).get(p)}function pe(d,p){z.get(d).delete(p),P.set(d,P.get(d).filter(function(g){return g!==p}))}function rt(d,p){requestAnimationFrame(function(){p.textContent=C.get(d).textContent,W.get(d).forEach(function(g){return p.sheet[g.method].apply(p.sheet,g.args)})})}function fe(d){if(!C.has(d))throw new TypeError("Illegal invocation")}function ke(){var d=this,p=document.createElement("style");e.body.appendChild(p),C.set(d,p),P.set(d,[]),z.set(d,new WeakMap),W.set(d,[])}var q=ke.prototype;q.replace=function(p){try{return this.replaceSync(p),Promise.resolve(this)}catch(g){return Promise.reject(g)}},q.replaceSync=function(p){if(fe(this),typeof p=="string"){var g=this;C.get(g).textContent=l(p),W.set(g,[]),P.get(g).forEach(function(S){S.isConnected()&&rt(g,I(g,S))})}},o(q,"cssRules",{configurable:!0,enumerable:!0,get:function(){return fe(this),C.get(this).sheet.cssRules}}),o(q,"media",{configurable:!0,enumerable:!0,get:function(){return fe(this),C.get(this).sheet.media}}),m.forEach(function(d){q[d]=function(){var p=this;fe(p);var g=arguments;W.get(p).push({method:d,args:g}),P.get(p).forEach(function(E){if(E.isConnected()){var w=I(p,E).sheet;w[d].apply(w,g)}});var S=C.get(p).sheet;return S[d].apply(S,g)}}),o(ke,Symbol.hasInstance,{configurable:!0,value:ue});var it={childList:!0,subtree:!0},ot=new WeakMap;function G(d){var p=ot.get(d);return p||(p=new lt(d),ot.set(d,p)),p}function st(d){o(d.prototype,"adoptedStyleSheets",{configurable:!0,enumerable:!0,get:function(){return G(this).sheets},set:function(p){G(this).update(p)}})}function Me(d,p){for(var g=document.createNodeIterator(d,NodeFilter.SHOW_ELEMENT,function(E){return u(E)?NodeFilter.FILTER_ACCEPT:NodeFilter.FILTER_REJECT},null,!1),S=void 0;S=g.nextNode();)p(u(S))}var me=new WeakMap,K=new WeakMap,ge=new WeakMap;function dn(d,p){return p instanceof HTMLStyleElement&&K.get(d).some(function(g){return I(g,d)})}function at(d){var p=me.get(d);return p instanceof Document?p.body:p}function Ue(d){var p=document.createDocumentFragment(),g=K.get(d),S=ge.get(d),E=at(d);S.disconnect(),g.forEach(function(w){p.appendChild(I(w,d)||Ne(w,d))}),E.insertBefore(p,null),S.observe(E,it),g.forEach(function(w){rt(w,I(w,d))})}function lt(d){var p=this;p.sheets=[],me.set(p,d),K.set(p,[]),ge.set(p,new MutationObserver(function(g,S){if(!document){S.disconnect();return}g.forEach(function(E){n||i.call(E.addedNodes,function(w){w instanceof Element&&Me(w,function(J){G(J).connect()})}),i.call(E.removedNodes,function(w){w instanceof Element&&(dn(p,w)&&Ue(p),n||Me(w,function(J){G(J).disconnect()}))})})}))}if(lt.prototype={isConnected:function(){var d=me.get(this);return d instanceof Document?d.readyState!=="loading":a(d.host)},connect:function(){var d=at(this);ge.get(this).observe(d,it),K.get(this).length>0&&Ue(this),Me(d,function(p){G(p).connect()})},disconnect:function(){ge.get(this).disconnect()},update:function(d){var p=this,g=me.get(p)===document?"Document":"ShadowRoot";if(!Array.isArray(d))throw new TypeError("Failed to set the 'adoptedStyleSheets' property on "+g+": Iterator getter is not callable.");if(!d.every(ue))throw new TypeError("Failed to set the 'adoptedStyleSheets' property on "+g+": Failed to convert value to 'CSSStyleSheet'");if(d.some(Ie))throw new TypeError("Failed to set the 'adoptedStyleSheets' property on "+g+": Can't adopt non-constructed stylesheets");p.sheets=d;var S=K.get(p),E=c(d),w=h(S,E);w.forEach(function(J){f(I(J,p)),pe(J,p)}),K.set(p,E),p.isConnected()&&E.length>0&&Ue(p)}},window.CSSStyleSheet=ke,st(Document),"ShadowRoot"in window){st(ShadowRoot);var ct=Element.prototype,hn=ct.attachShadow;ct.attachShadow=function(p){var g=hn.call(this,p);return p.mode==="closed"&&t.set(this,g),g}}var ve=G(document);ve.isConnected()?ve.connect():document.addEventListener("DOMContentLoaded",ve.connect.bind(ve))})();const{toString:xr}=Object.prototype;function Rr(n){return xr.call(n)==="[object RegExp]"}function Or(n,{preserve:e=!0,whitespace:t=!0,all:r}={}){if(r)throw new Error("The `all` option is no longer supported. Use the `preserve` option instead.");let o=e,i;typeof e=="function"?(o=!1,i=e):Rr(e)&&(o=!1,i=h=>e.test(h));let s=!1,l="",a="",c="";for(let h=0;h<n.length;h++){if(l=n[h],n[h-1]!=="\\"&&(l==='"'||l==="'")&&(s===l?s=!1:s||(s=l)),!s&&l==="/"&&n[h+1]==="*"){const f=n[h+2]==="!";let u=h+2;for(;u<n.length;u++){if(n[u]==="*"&&n[u+1]==="/"){o&&f||i&&i(a)?c+=`/*${a}*/`:t||(n[u+2]===`
`?u++:n[u+2]+n[u+3]===`\r
`&&(u+=2)),a="";break}a+=n[u]}h=u+1;continue}c+=l}return c}const Pr=CSSStyleSheet.toString().includes("document.createElement"),Lr=(n,e)=>{const t=/(?:@media\s(.+?))?(?:\s{)?\@import\s*(?:url\(\s*['"]?(.+?)['"]?\s*\)|(["'])((?:\\.|[^\\])*?)\3)([^;]*);(?:})?/g;/\/\*(.|[\r\n])*?\*\//gm.exec(n)!=null&&(n=Or(n));for(var r,o=n;(r=t.exec(n))!==null;){o=o.replace(r[0],"");const i=document.createElement("link");i.rel="stylesheet",i.href=r[2]||r[4];const s=r[1]||r[5];s&&(i.media=s),e===document?document.head.appendChild(i):e.appendChild(i)}return o},Ir=(n,e,t)=>(t?e.adoptedStyleSheets=[n,...e.adoptedStyleSheets]:e.adoptedStyleSheets=[...e.adoptedStyleSheets,n],()=>{e.adoptedStyleSheets=e.adoptedStyleSheets.filter(r=>r!==n)}),Nr=(n,e,t)=>{const r=new CSSStyleSheet;return r.replaceSync(n),Pr?Ir(r,e,t):(t?e.adoptedStyleSheets.splice(0,0,r):e.adoptedStyleSheets.push(r),()=>{e.adoptedStyleSheets.splice(e.adoptedStyleSheets.indexOf(r),1)})},kr=(n,e)=>{const t=document.createElement("style");t.type="text/css",t.textContent=n;let r;if(e){const i=Array.from(document.head.childNodes).filter(s=>s.nodeType===Node.COMMENT_NODE).find(s=>s.data.trim()===e);i&&(r=i)}return document.head.insertBefore(t,r),()=>{t.remove()}},We=(n,e,t,r)=>{if(t===document){const i=Mr(n);if(window.Vaadin.theme.injectedGlobalCss.indexOf(i)!==-1)return;window.Vaadin.theme.injectedGlobalCss.push(i)}const o=Lr(n,t);return t===document?kr(o,e):Nr(o,t,r)};window.Vaadin=window.Vaadin||{};window.Vaadin.theme=window.Vaadin.theme||{};window.Vaadin.theme.injectedGlobalCss=[];function Dt(n){let e,t,r=2166136261;for(e=0,t=n.length;e<t;e++)r^=n.charCodeAt(e),r+=(r<<1)+(r<<4)+(r<<7)+(r<<8)+(r<<24);return("0000000"+(r>>>0).toString(16)).substr(-8)}function Mr(n){let e=Dt(n);return e+Dt(e+n)}document._vaadintheme_hirepulse_componentCss||(document._vaadintheme_hirepulse_componentCss=!0);/**
 * @license
 * Copyright (c) 2021 - 2023 Vaadin Ltd.
 * This program is available under Apache License Version 2.0, available at https://vaadin.com/license/
 */function Ur(n){const e=customElements.get(n.is);if(!e)Object.defineProperty(n,"version",{get(){return"24.3.6"}}),customElements.define(n.is,n);else{const t=e.version;t&&n.version&&t===n.version?console.warn(`The component ${n.is} has been loaded twice`):console.error(`Tried to define ${n.is} version ${n.version} when version ${e.version} is already in use. Something will probably break.`)}}/**
 * @license
 * Copyright (c) 2017 - 2023 Vaadin Ltd.
 * This program is available under Apache License Version 2.0, available at https://vaadin.com/license/
 */class Dr extends HTMLElement{static get is(){return"vaadin-lumo-styles"}}Ur(Dr);/**
 * @license
 * Copyright (c) 2017 - 2023 Vaadin Ltd.
 * This program is available under Apache License Version 2.0, available at https://vaadin.com/license/
 */const Fr=n=>class extends n{static get properties(){return{_theme:{type:String,readOnly:!0}}}static get observedAttributes(){return[...super.observedAttributes,"theme"]}attributeChangedCallback(t,r,o){super.attributeChangedCallback(t,r,o),t==="theme"&&this._set_theme(o)}};/**
 * @license
 * Copyright (c) 2017 - 2023 Vaadin Ltd.
 * This program is available under Apache License Version 2.0, available at https://vaadin.com/license/
 */const sn=[];function an(n){return n&&Object.prototype.hasOwnProperty.call(n,"__themes")}function Vr(n){return an(customElements.get(n))}function jr(n=[]){return[n].flat(1/0).filter(e=>e instanceof Ze?!0:(console.warn("An item in styles is not of type CSSResult. Use `unsafeCSS` or `css`."),!1))}function ln(n,e,t={}){n&&Vr(n)&&console.warn(`The custom element definition for "${n}"
      was finalized before a style module was registered.
      Make sure to add component specific style modules before
      importing the corresponding custom element.`),e=jr(e),window.Vaadin&&window.Vaadin.styleModules?window.Vaadin.styleModules.registerStyles(n,e,t):sn.push({themeFor:n,styles:e,include:t.include,moduleId:t.moduleId})}function Je(){return window.Vaadin&&window.Vaadin.styleModules?window.Vaadin.styleModules.getAllThemes():sn}function Br(n,e){return(n||"").split(" ").some(t=>new RegExp(`^${t.split("*").join(".*")}$`,"u").test(e))}function Hr(n=""){let e=0;return n.startsWith("lumo-")||n.startsWith("material-")?e=1:n.startsWith("vaadin-")&&(e=2),e}function cn(n){const e=[];return n.include&&[].concat(n.include).forEach(t=>{const r=Je().find(o=>o.moduleId===t);r?e.push(...cn(r),...r.styles):console.warn(`Included moduleId ${t} not found in style registry`)},n.styles),e}function zr(n,e){const t=document.createElement("style");t.innerHTML=n.map(r=>r.cssText).join(`
`),e.content.appendChild(t)}function Wr(n){const e=`${n}-default-theme`,t=Je().filter(r=>r.moduleId!==e&&Br(r.themeFor,n)).map(r=>({...r,styles:[...cn(r),...r.styles],includePriority:Hr(r.moduleId)})).sort((r,o)=>o.includePriority-r.includePriority);return t.length>0?t:Je().filter(r=>r.moduleId===e)}const ii=n=>class extends Fr(n){static finalize(){if(super.finalize(),this.elementStyles)return;const t=this.prototype._template;!t||an(this)||zr(this.getStylesForThis(),t)}static finalizeStyles(t){const r=this.getStylesForThis();return t?[...super.finalizeStyles(t),...r]:r}static getStylesForThis(){const t=Object.getPrototypeOf(this.prototype),r=(t?t.constructor.__themes:[])||[];this.__themes=[...r,...Wr(this.is)];const o=this.__themes.flatMap(i=>i.styles);return o.filter((i,s)=>s===o.lastIndexOf(i))}};/**
 * @license
 * Copyright (c) 2017 - 2023 Vaadin Ltd.
 * This program is available under Apache License Version 2.0, available at https://vaadin.com/license/
 */const qr=(n,...e)=>{const t=document.createElement("style");t.id=n,t.textContent=e.map(r=>r.toString()).join(`
`).replace(":host","html"),document.head.insertAdjacentElement("afterbegin",t)},Le=(n,...e)=>{qr(`lumo-${n}`,e)};/**
 * @license
 * Copyright (c) 2017 - 2023 Vaadin Ltd.
 * This program is available under Apache License Version 2.0, available at https://vaadin.com/license/
 */const Gr=ee`
  :host {
    /* Base (background) */
    --lumo-base-color: #fff;

    /* Tint */
    --lumo-tint-5pct: hsla(0, 0%, 100%, 0.3);
    --lumo-tint-10pct: hsla(0, 0%, 100%, 0.37);
    --lumo-tint-20pct: hsla(0, 0%, 100%, 0.44);
    --lumo-tint-30pct: hsla(0, 0%, 100%, 0.5);
    --lumo-tint-40pct: hsla(0, 0%, 100%, 0.57);
    --lumo-tint-50pct: hsla(0, 0%, 100%, 0.64);
    --lumo-tint-60pct: hsla(0, 0%, 100%, 0.7);
    --lumo-tint-70pct: hsla(0, 0%, 100%, 0.77);
    --lumo-tint-80pct: hsla(0, 0%, 100%, 0.84);
    --lumo-tint-90pct: hsla(0, 0%, 100%, 0.9);
    --lumo-tint: #fff;

    /* Shade */
    --lumo-shade-5pct: hsla(214, 61%, 25%, 0.05);
    --lumo-shade-10pct: hsla(214, 57%, 24%, 0.1);
    --lumo-shade-20pct: hsla(214, 53%, 23%, 0.16);
    --lumo-shade-30pct: hsla(214, 50%, 22%, 0.26);
    --lumo-shade-40pct: hsla(214, 47%, 21%, 0.38);
    --lumo-shade-50pct: hsla(214, 45%, 20%, 0.52);
    --lumo-shade-60pct: hsla(214, 43%, 19%, 0.6);
    --lumo-shade-70pct: hsla(214, 42%, 18%, 0.69);
    --lumo-shade-80pct: hsla(214, 41%, 17%, 0.83);
    --lumo-shade-90pct: hsla(214, 40%, 16%, 0.94);
    --lumo-shade: hsl(214, 35%, 15%);

    /* Contrast */
    --lumo-contrast-5pct: var(--lumo-shade-5pct);
    --lumo-contrast-10pct: var(--lumo-shade-10pct);
    --lumo-contrast-20pct: var(--lumo-shade-20pct);
    --lumo-contrast-30pct: var(--lumo-shade-30pct);
    --lumo-contrast-40pct: var(--lumo-shade-40pct);
    --lumo-contrast-50pct: var(--lumo-shade-50pct);
    --lumo-contrast-60pct: var(--lumo-shade-60pct);
    --lumo-contrast-70pct: var(--lumo-shade-70pct);
    --lumo-contrast-80pct: var(--lumo-shade-80pct);
    --lumo-contrast-90pct: var(--lumo-shade-90pct);
    --lumo-contrast: var(--lumo-shade);

    /* Text */
    --lumo-header-text-color: var(--lumo-contrast);
    --lumo-body-text-color: var(--lumo-contrast-90pct);
    --lumo-secondary-text-color: var(--lumo-contrast-70pct);
    --lumo-tertiary-text-color: var(--lumo-contrast-50pct);
    --lumo-disabled-text-color: var(--lumo-contrast-30pct);

    /* Primary */
    --lumo-primary-color: hsl(214, 100%, 48%);
    --lumo-primary-color-50pct: hsla(214, 100%, 49%, 0.76);
    --lumo-primary-color-10pct: hsla(214, 100%, 60%, 0.13);
    --lumo-primary-text-color: hsl(214, 100%, 43%);
    --lumo-primary-contrast-color: #fff;

    /* Error */
    --lumo-error-color: hsl(3, 85%, 48%);
    --lumo-error-color-50pct: hsla(3, 85%, 49%, 0.5);
    --lumo-error-color-10pct: hsla(3, 85%, 49%, 0.1);
    --lumo-error-text-color: hsl(3, 89%, 42%);
    --lumo-error-contrast-color: #fff;

    /* Success */
    --lumo-success-color: hsl(145, 72%, 30%);
    --lumo-success-color-50pct: hsla(145, 72%, 31%, 0.5);
    --lumo-success-color-10pct: hsla(145, 72%, 31%, 0.1);
    --lumo-success-text-color: hsl(145, 85%, 25%);
    --lumo-success-contrast-color: #fff;

    /* Warning */
    --lumo-warning-color: hsl(48, 100%, 50%);
    --lumo-warning-color-10pct: hsla(48, 100%, 50%, 0.25);
    --lumo-warning-text-color: hsl(32, 100%, 30%);
    --lumo-warning-contrast-color: var(--lumo-shade-90pct);
  }

  /* forced-colors mode adjustments */
  @media (forced-colors: active) {
    html {
      --lumo-disabled-text-color: GrayText;
    }
  }
`;Le("color-props",Gr);const tt=ee`
  [theme~='dark'] {
    /* Base (background) */
    --lumo-base-color: hsl(214, 35%, 21%);

    /* Tint */
    --lumo-tint-5pct: hsla(214, 65%, 85%, 0.06);
    --lumo-tint-10pct: hsla(214, 60%, 80%, 0.14);
    --lumo-tint-20pct: hsla(214, 64%, 82%, 0.23);
    --lumo-tint-30pct: hsla(214, 69%, 84%, 0.32);
    --lumo-tint-40pct: hsla(214, 73%, 86%, 0.41);
    --lumo-tint-50pct: hsla(214, 78%, 88%, 0.5);
    --lumo-tint-60pct: hsla(214, 82%, 90%, 0.58);
    --lumo-tint-70pct: hsla(214, 87%, 92%, 0.69);
    --lumo-tint-80pct: hsla(214, 91%, 94%, 0.8);
    --lumo-tint-90pct: hsla(214, 96%, 96%, 0.9);
    --lumo-tint: hsl(214, 100%, 98%);

    /* Shade */
    --lumo-shade-5pct: hsla(214, 0%, 0%, 0.07);
    --lumo-shade-10pct: hsla(214, 4%, 2%, 0.15);
    --lumo-shade-20pct: hsla(214, 8%, 4%, 0.23);
    --lumo-shade-30pct: hsla(214, 12%, 6%, 0.32);
    --lumo-shade-40pct: hsla(214, 16%, 8%, 0.41);
    --lumo-shade-50pct: hsla(214, 20%, 10%, 0.5);
    --lumo-shade-60pct: hsla(214, 24%, 12%, 0.6);
    --lumo-shade-70pct: hsla(214, 28%, 13%, 0.7);
    --lumo-shade-80pct: hsla(214, 32%, 13%, 0.8);
    --lumo-shade-90pct: hsla(214, 33%, 13%, 0.9);
    --lumo-shade: hsl(214, 33%, 13%);

    /* Contrast */
    --lumo-contrast-5pct: var(--lumo-tint-5pct);
    --lumo-contrast-10pct: var(--lumo-tint-10pct);
    --lumo-contrast-20pct: var(--lumo-tint-20pct);
    --lumo-contrast-30pct: var(--lumo-tint-30pct);
    --lumo-contrast-40pct: var(--lumo-tint-40pct);
    --lumo-contrast-50pct: var(--lumo-tint-50pct);
    --lumo-contrast-60pct: var(--lumo-tint-60pct);
    --lumo-contrast-70pct: var(--lumo-tint-70pct);
    --lumo-contrast-80pct: var(--lumo-tint-80pct);
    --lumo-contrast-90pct: var(--lumo-tint-90pct);
    --lumo-contrast: var(--lumo-tint);

    /* Text */
    --lumo-header-text-color: var(--lumo-contrast);
    --lumo-body-text-color: var(--lumo-contrast-90pct);
    --lumo-secondary-text-color: var(--lumo-contrast-70pct);
    --lumo-tertiary-text-color: var(--lumo-contrast-50pct);
    --lumo-disabled-text-color: var(--lumo-contrast-30pct);

    /* Primary */
    --lumo-primary-color: hsl(214, 90%, 48%);
    --lumo-primary-color-50pct: hsla(214, 90%, 70%, 0.69);
    --lumo-primary-color-10pct: hsla(214, 90%, 55%, 0.13);
    --lumo-primary-text-color: hsl(214, 90%, 77%);
    --lumo-primary-contrast-color: #fff;

    /* Error */
    --lumo-error-color: hsl(3, 79%, 49%);
    --lumo-error-color-50pct: hsla(3, 75%, 62%, 0.5);
    --lumo-error-color-10pct: hsla(3, 75%, 62%, 0.14);
    --lumo-error-text-color: hsl(3, 100%, 80%);

    /* Success */
    --lumo-success-color: hsl(145, 72%, 30%);
    --lumo-success-color-50pct: hsla(145, 92%, 51%, 0.5);
    --lumo-success-color-10pct: hsla(145, 92%, 51%, 0.1);
    --lumo-success-text-color: hsl(145, 85%, 46%);

    /* Warning */
    --lumo-warning-color: hsl(43, 100%, 48%);
    --lumo-warning-color-10pct: hsla(40, 100%, 50%, 0.2);
    --lumo-warning-text-color: hsl(45, 100%, 60%);
    --lumo-warning-contrast-color: var(--lumo-shade-90pct);
  }

  html {
    color: var(--lumo-body-text-color);
    background-color: var(--lumo-base-color);
    color-scheme: light;
  }

  [theme~='dark'] {
    color: var(--lumo-body-text-color);
    background-color: var(--lumo-base-color);
    color-scheme: dark;
  }

  h1,
  h2,
  h3,
  h4,
  h5,
  h6 {
    color: var(--lumo-header-text-color);
  }

  a:where(:any-link) {
    color: var(--lumo-primary-text-color);
  }

  a:not(:any-link) {
    color: var(--lumo-disabled-text-color);
  }

  blockquote {
    color: var(--lumo-secondary-text-color);
  }

  code,
  pre {
    background-color: var(--lumo-contrast-10pct);
    border-radius: var(--lumo-border-radius-m);
  }
`;ln("",tt,{moduleId:"lumo-color"});/**
 * @license
 * Copyright (c) 2017 - 2023 Vaadin Ltd.
 * This program is available under Apache License Version 2.0, available at https://vaadin.com/license/
 */Le("color",tt);/**
 * @license
 * Copyright (c) 2017 - 2023 Vaadin Ltd.
 * This program is available under Apache License Version 2.0, available at https://vaadin.com/license/
 */const Kr=ee`
  :host {
    /* prettier-ignore */
    --lumo-font-family: -apple-system, BlinkMacSystemFont, 'Roboto', 'Segoe UI', Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol';

    /* Font sizes */
    --lumo-font-size-xxs: 0.75rem;
    --lumo-font-size-xs: 0.8125rem;
    --lumo-font-size-s: 0.875rem;
    --lumo-font-size-m: 1rem;
    --lumo-font-size-l: 1.125rem;
    --lumo-font-size-xl: 1.375rem;
    --lumo-font-size-xxl: 1.75rem;
    --lumo-font-size-xxxl: 2.5rem;

    /* Line heights */
    --lumo-line-height-xs: 1.25;
    --lumo-line-height-s: 1.375;
    --lumo-line-height-m: 1.625;
  }
`,nt=ee`
  body,
  :host {
    font-family: var(--lumo-font-family);
    font-size: var(--lumo-font-size-m);
    line-height: var(--lumo-line-height-m);
    -webkit-text-size-adjust: 100%;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
  }

  small,
  [theme~='font-size-s'] {
    font-size: var(--lumo-font-size-s);
    line-height: var(--lumo-line-height-s);
  }

  [theme~='font-size-xs'] {
    font-size: var(--lumo-font-size-xs);
    line-height: var(--lumo-line-height-xs);
  }

  :where(h1, h2, h3, h4, h5, h6) {
    font-weight: 600;
    line-height: var(--lumo-line-height-xs);
    margin-block: 0;
  }

  :where(h1) {
    font-size: var(--lumo-font-size-xxxl);
  }

  :where(h2) {
    font-size: var(--lumo-font-size-xxl);
  }

  :where(h3) {
    font-size: var(--lumo-font-size-xl);
  }

  :where(h4) {
    font-size: var(--lumo-font-size-l);
  }

  :where(h5) {
    font-size: var(--lumo-font-size-m);
  }

  :where(h6) {
    font-size: var(--lumo-font-size-xs);
    text-transform: uppercase;
    letter-spacing: 0.03em;
  }

  p,
  blockquote {
    margin-top: 0.5em;
    margin-bottom: 0.75em;
  }

  a {
    text-decoration: none;
  }

  a:where(:any-link):hover {
    text-decoration: underline;
  }

  hr {
    display: block;
    align-self: stretch;
    height: 1px;
    border: 0;
    padding: 0;
    margin: var(--lumo-space-s) calc(var(--lumo-border-radius-m) / 2);
    background-color: var(--lumo-contrast-10pct);
  }

  blockquote {
    border-left: 2px solid var(--lumo-contrast-30pct);
  }

  b,
  strong {
    font-weight: 600;
  }

  /* RTL specific styles */
  blockquote[dir='rtl'] {
    border-left: none;
    border-right: 2px solid var(--lumo-contrast-30pct);
  }
`;ln("",nt,{moduleId:"lumo-typography"});Le("typography-props",Kr);/**
 * @license
 * Copyright (c) 2017 - 2023 Vaadin Ltd.
 * This program is available under Apache License Version 2.0, available at https://vaadin.com/license/
 */const Jr=ee`
  ${Yt(nt.cssText.replace(/,\s*:host/su,""))}
`;Le("typography",Jr);const Qr=ee`@import"https://fonts.googleapis.com/css2?family=Plus+Jakarta+Sans:wght@300;400;500;600;700;800&family=JetBrains+Mono:wght@400;500;600&display=swap";:root{--hp-bg-primary: #0f172a;--hp-bg-surface: #1e293b;--hp-bg-card: rgba(30, 41, 59, .7);--hp-bg-card-hover: rgba(51, 65, 85, .8);--hp-border-color: rgba(255, 255, 255, .08);--hp-primary: #6366f1;--hp-primary-hover: #4f46e5;--hp-primary-light: rgba(99, 102, 241, .15);--hp-accent: #8b5cf6;--hp-success: #10b981;--hp-success-light: rgba(16, 185, 129, .15);--hp-warning: #f59e0b;--hp-warning-light: rgba(245, 158, 11, .15);--hp-danger: #ef4444;--hp-danger-light: rgba(239, 68, 68, .15);--hp-info: #06b6d4;--hp-text-main: #f8fafc;--hp-text-muted: #94a3b8;--hp-text-dim: #64748b;--hp-radius-sm: 8px;--hp-radius-md: 12px;--hp-radius-lg: 18px;--hp-radius-pill: 9999px;--hp-shadow-card: 0 10px 25px -5px rgba(0, 0, 0, .3), 0 8px 10px -6px rgba(0, 0, 0, .3);--hp-shadow-glow: 0 0 20px rgba(99, 102, 241, .25)}html,body{font-family:Plus Jakarta Sans,-apple-system,BlinkMacSystemFont,sans-serif;background-color:var(--hp-bg-primary);color:var(--hp-text-main);margin:0;padding:0}.hp-glass-card{background:var(--hp-bg-card);backdrop-filter:blur(16px);-webkit-backdrop-filter:blur(16px);border:1px solid var(--hp-border-color);border-radius:var(--hp-radius-md);box-shadow:var(--hp-shadow-card);padding:20px;transition:all .25s ease-in-out}.hp-glass-card:hover{border-color:#6366f14d;transform:translateY(-2px);box-shadow:var(--hp-shadow-card),var(--hp-shadow-glow)}.hp-metric-card{background:linear-gradient(135deg,#1e293be6,#0f172ae6);border:1px solid var(--hp-border-color);border-radius:var(--hp-radius-md);padding:22px;display:flex;flex-direction:column;justify-content:space-between;position:relative;overflow:hidden}.hp-metric-card:before{content:"";position:absolute;top:0;left:0;width:4px;height:100%;background:var(--hp-primary);border-radius:4px 0 0 4px}.hp-metric-value{font-size:2.2rem;font-weight:800;letter-spacing:-.02em;color:#fff;margin-top:8px}.hp-metric-label{font-size:.875rem;font-weight:600;text-transform:uppercase;letter-spacing:.05em;color:var(--hp-text-muted)}.hp-code-block{font-family:JetBrains Mono,monospace;background-color:#090d16;color:#e2e8f0;border-radius:var(--hp-radius-sm);padding:16px;border:1px solid rgba(255,255,255,.05);font-size:.9rem;line-height:1.6;white-space:pre-wrap;overflow-x:auto}.hp-kanban-column{background:#0f172a99;border:1px solid var(--hp-border-color);border-radius:var(--hp-radius-md);padding:16px;min-width:280px;flex:1}.hp-kanban-header{font-size:1rem;font-weight:700;display:flex;align-items:center;justify-content:space-between;margin-bottom:14px;padding-bottom:10px;border-bottom:1px solid var(--hp-border-color)}.hp-qa-card{background:#1e293b99;border:1px solid var(--hp-border-color);border-radius:var(--hp-radius-md);padding:18px;margin-bottom:12px;transition:all .2s ease}.hp-qa-card:hover{background:#28354bcc;border-color:var(--hp-primary)}.hp-badge{padding:4px 12px;border-radius:var(--hp-radius-pill);font-size:.75rem;font-weight:700;text-transform:uppercase;letter-spacing:.04em;display:inline-flex;align-items:center;gap:6px}.hp-badge-easy{background:var(--hp-success-light);color:var(--hp-success)}.hp-badge-medium{background:var(--hp-warning-light);color:var(--hp-warning)}.hp-badge-hard{background:var(--hp-danger-light);color:var(--hp-danger)}.hp-badge-primary{background:var(--hp-primary-light);color:#818cf8}::-webkit-scrollbar{width:8px;height:8px}::-webkit-scrollbar-track{background:var(--hp-bg-primary)}::-webkit-scrollbar-thumb{background:#334155;border-radius:4px}::-webkit-scrollbar-thumb:hover{background:#475569}`,Xr=n=>{const e=[];n!==document&&(e.push(We(tt.cssText,"",n,!0)),e.push(We(nt.cssText,"",n,!0)),e.push(We(Qr.toString(),"",n)))},Yr=Xr;Yr(document);export{ii as T,qe as _,Sr as a,sn as b,Yt as c,Ur as d,wr as e,Le as f,_ as g,ni as h,ee as i,vr as j,Fr as k,ln as r,se as s,br as t,B as w,dr as x};
function __vite__mapDeps(indexes) {
  if (!__vite__mapDeps.viteFileDeps) {
    __vite__mapDeps.viteFileDeps = []
  }
  return indexes.map((i) => __vite__mapDeps.viteFileDeps[i])
}
