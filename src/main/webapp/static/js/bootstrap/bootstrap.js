!function(A){A(function(){A.support.transition=(function(){var B=(function(){var E=document.createElement("bootstrap"),D={WebkitTransition:"webkitTransitionEnd",MozTransition:"transitionend",OTransition:"oTransitionEnd otransitionend",transition:"transitionend"},C;
for(C in D){if(E.style[C]!==undefined){return D[C]
}}}());
return B&&{end:B}
})()
})
}(window.jQuery);
!function(C){var B='[data-dismiss="alert"]',A=function(D){C(D).on("click",B,this.close)
};
A.prototype.close=function(H){var G=C(this),E=G.attr("data-target"),F;
if(!E){E=G.attr("href");
E=E&&E.replace(/.*(?=#[^\s]*$)/,"")
}F=C(E);
H&&H.preventDefault();
F.length||(F=G.hasClass("alert")?G:G.parent());
F.trigger(H=C.Event("close"));
if(H.isDefaultPrevented()){return 
}F.removeClass("in");
function D(){F.trigger("closed").remove()
}C.support.transition&&F.hasClass("fade")?F.on(C.support.transition.end,D):D()
};
C.fn.alert=function(D){return this.each(function(){var F=C(this),E=F.data("alert");
if(!E){F.data("alert",(E=new A(this)))
}if(typeof D=="string"){E[D].call(F)
}})
};
C.fn.alert.Constructor=A;
C(document).on("click.alert.data-api",B,A.prototype.close)
}(window.jQuery);
!function(B){var A=function(D,C){this.$element=B(D);
this.options=B.extend({},B.fn.button.defaults,C)
};
A.prototype.setState=function(E){var G="disabled",C=this.$element,D=C.data(),F=C.is("input")?"val":"html";
E=E+"Text";
D.resetText||C.data("resetText",C[F]());
C[F](D[E]||this.options[E]);
setTimeout(function(){E=="loadingText"?C.addClass(G).attr(G,G):C.removeClass(G).removeAttr(G)
},0)
};
A.prototype.toggle=function(){var C=this.$element.closest('[data-toggle="buttons-radio"]');
C&&C.find(".active").removeClass("active");
this.$element.toggleClass("active")
};
B.fn.button=function(C){return this.each(function(){var F=B(this),E=F.data("button"),D=typeof C=="object"&&C;
if(!E){F.data("button",(E=new A(this,D)))
}if(C=="toggle"){E.toggle()
}else{if(C){E.setState(C)
}}})
};
B.fn.button.defaults={loadingText:"loading..."};
B.fn.button.Constructor=A;
B(document).on("click.button.data-api","[data-toggle^=button]",function(D){var C=B(D.target);
if(!C.hasClass("btn")){C=C.closest(".btn")
}C.button("toggle")
})
}(window.jQuery);
!function(A){var B=function(D,C){this.$element=A(D);
this.options=C;
this.options.slide&&this.slide(this.options.slide);
this.options.pause=="hover"&&this.$element.on("mouseenter",A.proxy(this.pause,this)).on("mouseleave",A.proxy(this.cycle,this))
};
B.prototype={cycle:function(C){if(!C){this.paused=false
}this.options.interval&&!this.paused&&(this.interval=setInterval(A.proxy(this.next,this),this.options.interval));
return this
},to:function(G){var C=this.$element.find(".item.active"),D=C.parent().children(),E=D.index(C),F=this;
if(G>(D.length-1)||G<0){return 
}if(this.sliding){return this.$element.one("slid",function(){F.to(G)
})
}if(E==G){return this.pause().cycle()
}return this.slide(G>E?"next":"prev",A(D[G]))
},pause:function(C){if(!C){this.paused=true
}if(this.$element.find(".next, .prev").length&&A.support.transition.end){this.$element.trigger(A.support.transition.end);
this.cycle()
}clearInterval(this.interval);
this.interval=null;
return this
},next:function(){if(this.sliding){return 
}return this.slide("next")
},prev:function(){if(this.sliding){return 
}return this.slide("prev")
},slide:function(I,D){var K=this.$element.find(".item.active"),C=D||K[I](),H=this.interval,J=I=="next"?"left":"right",E=I=="next"?"first":"last",F=this,G;
this.sliding=true;
H&&this.pause();
C=C.length?C:this.$element.find(".item")[E]();
G=A.Event("slide",{relatedTarget:C[0]});
if(C.hasClass("active")){return 
}if(A.support.transition&&this.$element.hasClass("slide")){this.$element.trigger(G);
if(G.isDefaultPrevented()){return 
}C.addClass(I);
C[0].offsetWidth;
K.addClass(J);
C.addClass(J);
this.$element.one(A.support.transition.end,function(){C.removeClass([I,J].join(" ")).addClass("active");
K.removeClass(["active",J].join(" "));
F.sliding=false;
setTimeout(function(){F.$element.trigger("slid")
},0)
})
}else{this.$element.trigger(G);
if(G.isDefaultPrevented()){return 
}K.removeClass("active");
C.addClass("active");
this.sliding=false;
this.$element.trigger("slid")
}H&&this.cycle();
return this
}};
A.fn.carousel=function(C){return this.each(function(){var G=A(this),F=G.data("carousel"),D=A.extend({},A.fn.carousel.defaults,typeof C=="object"&&C),E=typeof C=="string"?C:D.slide;
if(!F){G.data("carousel",(F=new B(this,D)))
}if(typeof C=="number"){F.to(C)
}else{if(E){F[E]()
}else{if(D.interval){F.cycle()
}}}})
};
A.fn.carousel.defaults={interval:5000,pause:"hover"};
A.fn.carousel.Constructor=B;
A(document).on("click.carousel.data-api","[data-slide]",function(G){var F=A(this),D,C=A(F.attr("data-target")||(D=F.attr("href"))&&D.replace(/.*(?=#[^\s]+$)/,"")),E=A.extend({},C.data(),F.data());
C.carousel(E);
G.preventDefault()
})
}(window.jQuery);
!function(A){var B=function(D,C){this.$element=A(D);
this.options=A.extend({},A.fn.collapse.defaults,C);
if(this.options.parent){this.$parent=A(this.options.parent)
}this.options.toggle&&this.toggle()
};
B.prototype={constructor:B,dimension:function(){var C=this.$element.hasClass("width");
return C?"width":"height"
},show:function(){var F,C,E,D;
if(this.transitioning){return 
}F=this.dimension();
C=A.camelCase(["scroll",F].join("-"));
E=this.$parent&&this.$parent.find("> .accordion-group > .in");
if(E&&E.length){D=E.data("collapse");
if(D&&D.transitioning){return 
}E.collapse("hide");
D||E.data("collapse",null)
}this.$element[F](0);
this.transition("addClass",A.Event("show"),"shown");
A.support.transition&&this.$element[F](this.$element[0][C])
},hide:function(){var C;
if(this.transitioning){return 
}C=this.dimension();
this.reset(this.$element[C]());
this.transition("removeClass",A.Event("hide"),"hidden");
this.$element[C](0)
},reset:function(C){var D=this.dimension();
this.$element.removeClass("collapse")[D](C||"auto")[0].offsetWidth;
this.$element[C!==null?"addClass":"removeClass"]("collapse");
return this
},transition:function(G,D,E){var F=this,C=function(){if(D.type=="show"){F.reset()
}F.transitioning=0;
F.$element.trigger(E)
};
this.$element.trigger(D);
if(D.isDefaultPrevented()){return 
}this.transitioning=1;
this.$element[G]("in");
A.support.transition&&this.$element.hasClass("collapse")?this.$element.one(A.support.transition.end,C):C()
},toggle:function(){this[this.$element.hasClass("in")?"hide":"show"]()
}};
A.fn.collapse=function(C){return this.each(function(){var F=A(this),E=F.data("collapse"),D=typeof C=="object"&&C;
if(!E){F.data("collapse",(E=new B(this,D)))
}if(typeof C=="string"){E[C]()
}})
};
A.fn.collapse.defaults={toggle:true};
A.fn.collapse.Constructor=B;
A(document).on("click.collapse.data-api","[data-toggle=collapse]",function(G){var F=A(this),C,E=F.attr("data-target")||G.preventDefault()||(C=F.attr("href"))&&C.replace(/.*(?=#[^\s]+$)/,""),D=A(E).data("collapse")?"toggle":F.data();
F[A(E).hasClass("in")?"addClass":"removeClass"]("collapsed");
A(E).collapse(D)
})
}(window.jQuery);
!function(E){var B="[data-toggle=dropdown]",A=function(G){var F=E(G).on("click.dropdown.data-api",this.toggle);
E("html").on("click.dropdown.data-api",function(){F.parent().removeClass("open")
})
};
A.prototype={constructor:A,toggle:function(I){var H=E(this),G,F;
if(H.is(".disabled, :disabled")){return 
}G=D(H);
F=G.hasClass("open");
C();
if(!F){G.toggleClass("open");
H.focus()
}return false
},keydown:function(K){var J,L,F,I,H,G;
if(!/(38|40|27)/.test(K.keyCode)){return 
}J=E(this);
K.preventDefault();
K.stopPropagation();
if(J.is(".disabled, :disabled")){return 
}I=D(J);
H=I.hasClass("open");
if(!H||(H&&K.keyCode==27)){return J.click()
}L=E("[role=menu] li:not(.divider) a",I);
if(!L.length){return 
}G=L.index(L.filter(":focus"));
if(K.keyCode==38&&G>0){G--
}if(K.keyCode==40&&G<L.length-1){G++
}if(!~G){G=0
}L.eq(G).focus()
}};
function C(){E(B).each(function(){D(E(this)).removeClass("open")
})
}function D(H){var F=H.attr("data-target"),G;
if(!F){F=H.attr("href");
F=F&&/#/.test(F)&&F.replace(/.*(?=#[^\s]*$)/,"")
}G=E(F);
G.length||(G=H.parent());
return G
}E.fn.dropdown=function(F){return this.each(function(){var H=E(this),G=H.data("dropdown");
if(!G){H.data("dropdown",(G=new A(this)))
}if(typeof F=="string"){G[F].call(H)
}})
};
E.fn.dropdown.Constructor=A;
E(document).on("click.dropdown.data-api touchstart.dropdown.data-api",C).on("click.dropdown touchstart.dropdown.data-api",".dropdown form",function(F){F.stopPropagation()
}).on("click.dropdown.data-api touchstart.dropdown.data-api",B,A.prototype.toggle).on("keydown.dropdown.data-api touchstart.dropdown.data-api",B+", [role=menu]",A.prototype.keydown)
}(window.jQuery);
!function(B){var A=function(D,C){this.options=C;
this.$element=B(D).delegate('[data-dismiss="modal"]',"click.dismiss.modal",B.proxy(this.hide,this));
this.options.remote&&this.$element.find(".modal-body").load(this.options.remote)
};
A.prototype={constructor:A,toggle:function(){return this[!this.isShown?"show":"hide"]()
},show:function(){var C=this,D=B.Event("show");
this.$element.trigger(D);
if(this.isShown||D.isDefaultPrevented()){return 
}this.isShown=true;
this.escape();
this.backdrop(function(){var E=B.support.transition&&C.$element.hasClass("fade");
if(!C.$element.parent().length){C.$element.appendTo(document.body)
}C.$element.show();
if(E){C.$element[0].offsetWidth
}C.$element.addClass("in").attr("aria-hidden",false);
C.enforceFocus();
E?C.$element.one(B.support.transition.end,function(){C.$element.focus().trigger("shown")
}):C.$element.focus().trigger("shown")
})
},hide:function(D){D&&D.preventDefault();
var C=this;
D=B.Event("hide");
this.$element.trigger(D);
if(!this.isShown||D.isDefaultPrevented()){return 
}this.isShown=false;
this.escape();
B(document).off("focusin.modal");
this.$element.removeClass("in").attr("aria-hidden",true);
B.support.transition&&this.$element.hasClass("fade")?this.hideWithTransition():this.hideModal()
},enforceFocus:function(){var C=this;
B(document).on("focusin.modal",function(D){if(C.$element[0]!==D.target&&!C.$element.has(D.target).length){C.$element.focus()
}})
},escape:function(){var C=this;
if(this.isShown&&this.options.keyboard){this.$element.on("keyup.dismiss.modal",function(D){D.which==27&&C.hide()
})
}else{if(!this.isShown){this.$element.off("keyup.dismiss.modal")
}}},hideWithTransition:function(){var C=this,D=setTimeout(function(){C.$element.off(B.support.transition.end);
C.hideModal()
},500);
this.$element.one(B.support.transition.end,function(){clearTimeout(D);
C.hideModal()
})
},hideModal:function(C){this.$element.hide().trigger("hidden");
this.backdrop()
},removeBackdrop:function(){this.$backdrop.remove();
this.$backdrop=null
},backdrop:function(F){var E=this,D=this.$element.hasClass("fade")?"fade":"";
if(this.isShown&&this.options.backdrop){var C=B.support.transition&&D;
this.$backdrop=B('<div class="modal-backdrop '+D+'" />').appendTo(document.body);
this.$backdrop.click(this.options.backdrop=="static"?B.proxy(this.$element[0].focus,this.$element[0]):B.proxy(this.hide,this));
if(C){this.$backdrop[0].offsetWidth
}this.$backdrop.addClass("in");
C?this.$backdrop.one(B.support.transition.end,F):F()
}else{if(!this.isShown&&this.$backdrop){this.$backdrop.removeClass("in");
B.support.transition&&this.$element.hasClass("fade")?this.$backdrop.one(B.support.transition.end,B.proxy(this.removeBackdrop,this)):this.removeBackdrop()
}else{if(F){F()
}}}}};
B.fn.modal=function(C){return this.each(function(){var F=B(this),E=F.data("modal"),D=B.extend({},B.fn.modal.defaults,F.data(),typeof C=="object"&&C);
if(!E){F.data("modal",(E=new A(this,D)))
}if(typeof C=="string"){E[C]()
}else{if(D.show){E.show()
}}})
};
B.fn.modal.defaults={backdrop:true,keyboard:true,show:true};
B.fn.modal.Constructor=A;
B(document).on("click.modal.data-api",'[data-toggle="modal"]',function(G){var F=B(this),D=F.attr("href"),C=B(F.attr("data-target")||(D&&D.replace(/.*(?=#[^\s]+$)/,""))),E=C.data("modal")?"toggle":B.extend({remote:!/#/.test(D)&&D},C.data(),F.data());
G.preventDefault();
C.modal(E).one("hide",function(){F.focus()
})
})
}(window.jQuery);
!function(B){var A=function(D,C){this.init("tooltip",D,C)
};
A.prototype={constructor:A,init:function(F,E,D){var G,C;
this.type=F;
this.$element=B(E);
this.options=this.getOptions(D);
this.enabled=true;
if(this.options.trigger=="click"){this.$element.on("click."+this.type,this.options.selector,B.proxy(this.toggle,this))
}else{if(this.options.trigger!="manual"){G=this.options.trigger=="hover"?"mouseenter":"focus";
C=this.options.trigger=="hover"?"mouseleave":"blur";
this.$element.on(G+"."+this.type,this.options.selector,B.proxy(this.enter,this));
this.$element.on(C+"."+this.type,this.options.selector,B.proxy(this.leave,this))
}}this.options.selector?(this._options=B.extend({},this.options,{trigger:"manual",selector:""})):this.fixTitle()
},getOptions:function(C){C=B.extend({},B.fn[this.type].defaults,C,this.$element.data());
if(C.delay&&typeof C.delay=="number"){C.delay={show:C.delay,hide:C.delay}
}return C
},enter:function(D){var C=B(D.currentTarget)[this.type](this._options).data(this.type);
if(!C.options.delay||!C.options.delay.show){return C.show()
}clearTimeout(this.timeout);
C.hoverState="in";
this.timeout=setTimeout(function(){if(C.hoverState=="in"){C.show()
}},C.options.delay.show)
},leave:function(D){var C=B(D.currentTarget)[this.type](this._options).data(this.type);
if(this.timeout){clearTimeout(this.timeout)
}if(!C.options.delay||!C.options.delay.hide){return C.hide()
}C.hoverState="out";
this.timeout=setTimeout(function(){if(C.hoverState=="out"){C.hide()
}},C.options.delay.hide)
},show:function(){var G,C,I,E,H,D,F;
if(this.hasContent()&&this.enabled){G=this.tip();
this.setContent();
if(this.options.animation){G.addClass("fade")
}D=typeof this.options.placement=="function"?this.options.placement.call(this,G[0],this.$element[0]):this.options.placement;
C=/in/.test(D);
G.detach().css({top:0,left:0,display:"block"}).insertAfter(this.$element);
I=this.getPosition(C);
E=G[0].offsetWidth;
H=G[0].offsetHeight;
switch(C?D.split(" ")[1]:D){case"bottom":F={top:I.top+I.height,left:I.left+I.width/2-E/2};
break;
case"top":F={top:I.top-H,left:I.left+I.width/2-E/2};
break;
case"left":F={top:I.top+I.height/2-H/2,left:I.left-E};
break;
case"right":F={top:I.top+I.height/2-H/2,left:I.left+I.width};
break
}G.offset(F).addClass(D).addClass("in")
}},setContent:function(){var D=this.tip(),C=this.getTitle();
D.find(".tooltip-inner")[this.options.html?"html":"text"](C);
D.removeClass("fade in top bottom left right")
},hide:function(){var C=this,D=this.tip();
D.removeClass("in");
function E(){var F=setTimeout(function(){D.off(B.support.transition.end).detach()
},500);
D.one(B.support.transition.end,function(){clearTimeout(F);
D.detach()
})
}B.support.transition&&this.$tip.hasClass("fade")?E():D.detach();
return this
},fixTitle:function(){var C=this.$element;
if(C.attr("title")||typeof (C.attr("data-original-title"))!="string"){C.attr("data-original-title",C.attr("title")||"").removeAttr("title")
}},hasContent:function(){return this.getTitle()
},getPosition:function(C){return B.extend({},(C?{top:0,left:0}:this.$element.offset()),{width:this.$element[0].offsetWidth,height:this.$element[0].offsetHeight})
},getTitle:function(){var E,C=this.$element,D=this.options;
E=C.attr("data-original-title")||(typeof D.title=="function"?D.title.call(C[0]):D.title);
return E
},tip:function(){return this.$tip=this.$tip||B(this.options.template)
},validate:function(){if(!this.$element[0].parentNode){this.hide();
this.$element=null;
this.options=null
}},enable:function(){this.enabled=true
},disable:function(){this.enabled=false
},toggleEnabled:function(){this.enabled=!this.enabled
},toggle:function(D){var C=B(D.currentTarget)[this.type](this._options).data(this.type);
C[C.tip().hasClass("in")?"hide":"show"]()
},destroy:function(){this.hide().$element.off("."+this.type).removeData(this.type)
}};
B.fn.tooltip=function(C){return this.each(function(){var F=B(this),E=F.data("tooltip"),D=typeof C=="object"&&C;
if(!E){F.data("tooltip",(E=new A(this,D)))
}if(typeof C=="string"){E[C]()
}})
};
B.fn.tooltip.Constructor=A;
B.fn.tooltip.defaults={animation:true,placement:"top",selector:false,template:'<div class="tooltip"><div class="tooltip-arrow"></div><div class="tooltip-inner"></div></div>',trigger:"hover",title:"",delay:0,html:false}
}(window.jQuery);
!function(B){var A=function(D,C){this.init("popover",D,C)
};
A.prototype=B.extend({},B.fn.tooltip.Constructor.prototype,{constructor:A,setContent:function(){var E=this.tip(),D=this.getTitle(),C=this.getContent();
E.find(".popover-title")[this.options.html?"html":"text"](D);
E.find(".popover-content > *")[this.options.html?"html":"text"](C);
E.removeClass("fade top bottom left right in")
},hasContent:function(){return this.getTitle()||this.getContent()
},getContent:function(){var D,C=this.$element,E=this.options;
D=C.attr("data-content")||(typeof E.content=="function"?E.content.call(C[0]):E.content);
return D
},tip:function(){if(!this.$tip){this.$tip=B(this.options.template)
}return this.$tip
},destroy:function(){this.hide().$element.off("."+this.type).removeData(this.type)
}});
B.fn.popover=function(C){return this.each(function(){var F=B(this),E=F.data("popover"),D=typeof C=="object"&&C;
if(!E){F.data("popover",(E=new A(this,D)))
}if(typeof C=="string"){E[C]()
}})
};
B.fn.popover.Constructor=A;
B.fn.popover.defaults=B.extend({},B.fn.tooltip.defaults,{placement:"right",trigger:"click",content:"",template:'<div class="popover"><div class="arrow"></div><div class="popover-inner"><h3 class="popover-title"></h3><div class="popover-content"><p></p></div></div></div>'})
}(window.jQuery);
!function(B){function A(F,E){var G=B.proxy(this.process,this),C=B(F).is("body")?B(window):B(F),D;
this.options=B.extend({},B.fn.scrollspy.defaults,E);
this.$scrollElement=C.on("scroll.scroll-spy.data-api",G);
this.selector=(this.options.target||((D=B(F).attr("href"))&&D.replace(/.*(?=#[^\s]+$)/,""))||"")+" .nav li > a";
this.$body=B("body");
this.refresh();
this.process()
}A.prototype={constructor:A,refresh:function(){var C=this,D;
this.offsets=B([]);
this.targets=B([]);
D=this.$body.find(this.selector).map(function(){var F=B(this),E=F.data("target")||F.attr("href"),G=/^#\w/.test(E)&&B(E);
return(G&&G.length&&[[G.position().top,E]])||null
}).sort(function(F,E){return F[0]-E[0]
}).each(function(){C.offsets.push(this[0]);
C.targets.push(this[1])
})
},process:function(){var H=this.$scrollElement.scrollTop()+this.options.offset,E=this.$scrollElement[0].scrollHeight||this.$body[0].scrollHeight,G=E-this.$scrollElement.height(),F=this.offsets,C=this.targets,I=this.activeTarget,D;
if(H>=G){return I!=(D=C.last()[0])&&this.activate(D)
}for(D=F.length;
D--;
){I!=C[D]&&H>=F[D]&&(!F[D+1]||H<=F[D+1])&&this.activate(C[D])
}},activate:function(E){var D,C;
this.activeTarget=E;
B(this.selector).parent(".active").removeClass("active");
C=this.selector+'[data-target="'+E+'"],'+this.selector+'[href="'+E+'"]';
D=B(C).parent("li").addClass("active");
if(D.parent(".dropdown-menu").length){D=D.closest("li.dropdown").addClass("active")
}D.trigger("activate")
}};
B.fn.scrollspy=function(C){return this.each(function(){var F=B(this),E=F.data("scrollspy"),D=typeof C=="object"&&C;
if(!E){F.data("scrollspy",(E=new A(this,D)))
}if(typeof C=="string"){E[C]()
}})
};
B.fn.scrollspy.Constructor=A;
B.fn.scrollspy.defaults={offset:10};
B(window).on("load",function(){B('[data-spy="scroll"]').each(function(){var C=B(this);
C.scrollspy(C.data())
})
})
}(window.jQuery);
!function(B){var A=function(C){this.element=B(C)
};
A.prototype={constructor:A,show:function(){var H=this.element,E=H.closest("ul:not(.dropdown-menu)"),D=H.attr("data-target"),F,C,G;
if(!D){D=H.attr("href");
D=D&&D.replace(/.*(?=#[^\s]*$)/,"")
}if(H.parent("li").hasClass("active")){return 
}F=E.find(".active:last a")[0];
G=B.Event("show",{relatedTarget:F});
H.trigger(G);
if(G.isDefaultPrevented()){return 
}C=B(D);
this.activate(H.parent("li"),E);
this.activate(C,C.parent(),function(){H.trigger({type:"shown",relatedTarget:F})
})
},activate:function(E,D,H){var C=D.find("> .active"),G=H&&B.support.transition&&C.hasClass("fade");
function F(){C.removeClass("active").find("> .dropdown-menu > .active").removeClass("active");
E.addClass("active");
if(G){E[0].offsetWidth;
E.addClass("in")
}else{E.removeClass("fade")
}if(E.parent(".dropdown-menu")){E.closest("li.dropdown").addClass("active")
}H&&H()
}G?C.one(B.support.transition.end,F):F();
C.removeClass("in")
}};
B.fn.tab=function(C){return this.each(function(){var E=B(this),D=E.data("tab");
if(!D){E.data("tab",(D=new A(this)))
}if(typeof C=="string"){D[C]()
}})
};
B.fn.tab.Constructor=A;
B(document).on("click.tab.data-api",'[data-toggle="tab"], [data-toggle="pill"]',function(C){C.preventDefault();
B(this).tab("show")
})
}(window.jQuery);
!function(A){var B=function(D,C){this.$element=A(D);
this.options=A.extend({},A.fn.typeahead.defaults,C);
this.matcher=this.options.matcher||this.matcher;
this.sorter=this.options.sorter||this.sorter;
this.highlighter=this.options.highlighter||this.highlighter;
this.updater=this.options.updater||this.updater;
this.$menu=A(this.options.menu).appendTo("body");
this.source=this.options.source;
this.shown=false;
this.listen()
};
B.prototype={constructor:B,select:function(){var C=this.$menu.find(".active").attr("data-value");
this.$element.val(this.updater(C)).change();
return this.hide()
},updater:function(C){return C
},show:function(){var C=A.extend({},this.$element.offset(),{height:this.$element[0].offsetHeight});
this.$menu.css({top:C.top+C.height,left:C.left});
this.$menu.show();
this.shown=true;
return this
},hide:function(){this.$menu.hide();
this.shown=false;
return this
},lookup:function(D){var C;
this.query=this.$element.val();
if(!this.query||this.query.length<this.options.minLength){return this.shown?this.hide():this
}C=A.isFunction(this.source)?this.source(this.query,A.proxy(this.process,this)):this.source;
return C?this.process(C):this
},process:function(C){var D=this;
C=A.grep(C,function(E){return D.matcher(E)
});
C=this.sorter(C);
if(!C.length){return this.shown?this.hide():this
}return this.render(C.slice(0,this.options.items)).show()
},matcher:function(C){return ~C.toLowerCase().indexOf(this.query.toLowerCase())
},sorter:function(E){var F=[],D=[],C=[],G;
while(G=E.shift()){if(!G.toLowerCase().indexOf(this.query.toLowerCase())){F.push(G)
}else{if(~G.indexOf(this.query)){D.push(G)
}else{C.push(G)
}}}return F.concat(D,C)
},highlighter:function(C){var D=this.query.replace(/[\-\[\]{}()*+?.,\\\^$|#\s]/g,"\\$&");
return C.replace(new RegExp("("+D+")","ig"),function(E,F){return"<strong>"+F+"</strong>"
})
},render:function(C){var D=this;
C=A(C).map(function(E,F){E=A(D.options.item).attr("data-value",F);
E.find("a").html(D.highlighter(F));
return E[0]
});
C.first().addClass("active");
this.$menu.html(C);
return this
},next:function(D){var E=this.$menu.find(".active").removeClass("active"),C=E.next();
if(!C.length){C=A(this.$menu.find("li")[0])
}C.addClass("active")
},prev:function(D){var E=this.$menu.find(".active").removeClass("active"),C=E.prev();
if(!C.length){C=this.$menu.find("li").last()
}C.addClass("active")
},listen:function(){this.$element.on("blur",A.proxy(this.blur,this)).on("keypress",A.proxy(this.keypress,this)).on("keyup",A.proxy(this.keyup,this));
if(this.eventSupported("keydown")){this.$element.on("keydown",A.proxy(this.keydown,this))
}this.$menu.on("click",A.proxy(this.click,this)).on("mouseenter","li",A.proxy(this.mouseenter,this))
},eventSupported:function(C){var D=C in this.$element;
if(!D){this.$element.setAttribute(C,"return;");
D=typeof this.$element[C]==="function"
}return D
},move:function(C){if(!this.shown){return 
}switch(C.keyCode){case 9:case 13:case 27:C.preventDefault();
break;
case 38:C.preventDefault();
this.prev();
break;
case 40:C.preventDefault();
this.next();
break
}C.stopPropagation()
},keydown:function(C){this.suppressKeyPressRepeat=!~A.inArray(C.keyCode,[40,38,9,13,27]);
this.move(C)
},keypress:function(C){if(this.suppressKeyPressRepeat){return 
}this.move(C)
},keyup:function(C){switch(C.keyCode){case 40:case 38:case 16:case 17:case 18:break;
case 9:case 13:if(!this.shown){return 
}this.select();
break;
case 27:if(!this.shown){return 
}this.hide();
break;
default:this.lookup()
}C.stopPropagation();
C.preventDefault()
},blur:function(D){var C=this;
setTimeout(function(){C.hide()
},150)
},click:function(C){C.stopPropagation();
C.preventDefault();
this.select()
},mouseenter:function(C){this.$menu.find(".active").removeClass("active");
A(C.currentTarget).addClass("active")
}};
A.fn.typeahead=function(C){return this.each(function(){var F=A(this),E=F.data("typeahead"),D=typeof C=="object"&&C;
if(!E){F.data("typeahead",(E=new B(this,D)))
}if(typeof C=="string"){E[C]()
}})
};
A.fn.typeahead.defaults={source:[],items:8,menu:'<ul class="typeahead dropdown-menu"></ul>',item:'<li><a href="#"></a></li>',minLength:1};
A.fn.typeahead.Constructor=B;
A(document).on("focus.typeahead.data-api",'[data-provide="typeahead"]',function(D){var C=A(this);
if(C.data("typeahead")){return 
}D.preventDefault();
C.typeahead(C.data())
})
}(window.jQuery);
!function(B){var A=function(D,C){this.options=B.extend({},B.fn.affix.defaults,C);
this.$window=B(window).on("scroll.affix.data-api",B.proxy(this.checkPosition,this)).on("click.affix.data-api",B.proxy(function(){setTimeout(B.proxy(this.checkPosition,this),1)
},this));
this.$element=B(D);
this.checkPosition()
};
A.prototype.checkPosition=function(){if(!this.$element.is(":visible")){return 
}var G=B(document).height(),I=this.$window.scrollTop(),C=this.$element.offset(),J=this.options.offset,E=J.bottom,F=J.top,H="affix affix-top affix-bottom",D;
if(typeof J!="object"){E=F=J
}if(typeof F=="function"){F=J.top()
}if(typeof E=="function"){E=J.bottom()
}D=this.unpin!=null&&(I+this.unpin<=C.top)?false:E!=null&&(C.top+this.$element.height()>=G-E)?"bottom":F!=null&&I<=F?"top":false;
if(this.affixed===D){return 
}this.affixed=D;
this.unpin=D=="bottom"?C.top-I:null;
this.$element.removeClass(H).addClass("affix"+(D?"-"+D:""))
};
B.fn.affix=function(C){return this.each(function(){var F=B(this),E=F.data("affix"),D=typeof C=="object"&&C;
if(!E){F.data("affix",(E=new A(this,D)))
}if(typeof C=="string"){E[C]()
}})
};
B.fn.affix.Constructor=A;
B.fn.affix.defaults={offset:0};
B(window).on("load",function(){B('[data-spy="affix"]').each(function(){var D=B(this),C=D.data();
C.offset=C.offset||{};
C.offsetBottom&&(C.offset.bottom=C.offsetBottom);
C.offsetTop&&(C.offset.top=C.offsetTop);
D.affix(C)
})
})
}(window.jQuery);