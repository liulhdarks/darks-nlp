/**
 * 
 * Copyright 2015 The Darks NLP Project (Liu lihua)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package darks.nlp.common;


/**
 * Word's nature tagger
 * 
 * @author lihua.llh
 *
 */
public class NatureTagger
{
	
	public enum NatureTag
	{
	    /**
	     * 区别语素
	     */
	    bg,

	    /**
	     * 数语素
	     */
	    mg,

	    /**
	     * 名词性惯用语
	     */
	    nl,

	    /**
	     * 字母专名
	     */
	    nx,

	    /**
	     * 量词语素
	     */
	    qg,

	    /**
	     * 助词
	     */
	    ud,

	    /**
	     * 助词
	     */
	    uj,

	    /**
	     * 着
	     */
	    uz,

	    /**
	     * 过
	     */
	    ug,

	    /**
	     * 连词
	     */
	    ul,

	    /**
	     * 连词
	     */
	    uv,

	    /**
	     * 语气语素
	     */
	    yg,

	    /**
	     * 状态词
	     */
	    zg,

	    // 以上标签来自ICT，以下标签来自北大

	    /**
	     * 名词
	     */
	    n,

	    /**
	     * 人名
	     */
	    nr,

	    /**
	     * 日语人名
	     */
	    nrj,

	    /**
	     * 音译人名
	     */
	    nrf,

	    /**
	     * 复姓
	     */
	    nr1,

	    /**
	     * 蒙古姓名
	     */
	    nr2,

	    /**
	     * 地名
	     */
	    ns,

	    /**
	     * 音译地名
	     */
	    nsf,

	    /**
	     * 机构团体名
	     */
	    nt,

	    /**
	     * 公司名
	     */
	    ntc,

	    /**
	     * 工厂
	     */
	    ntcf,

	    /**
	     * 银行
	     */
	    ntcb,

	    /**
	     * 酒店宾馆
	     */
	    ntch,

	    /**
	     * 政府机构
	     */
	    nto,

	    /**
	     * 大学
	     */
	    ntu,

	    /**
	     * 中小学
	     */
	    nts,

	    /**
	     * 医院
	     */
	    nth,

	    /**
	     * 医药疾病等健康相关名词
	     */
	    nh,

	    /**
	     * 药品
	     */
	    nhm,

	    /**
	     * 疾病
	     */
	    nhd,

	    /**
	     * 工作相关名词
	     */
	    nn,

	    /**
	     * 职务职称
	     */
	    nnt,

	    /**
	     * 职业
	     */
	    nnd,

	    /**
	     * 名词性语素
	     */
	    ng,

	    /**
	     * 食品，比如“薯片”
	     */
	    nf,

	    /**
	     * 机构相关（不是独立机构名）
	     */
	    ni,

	    /**
	     * 教育相关机构
	     */
	    nit,

	    /**
	     * 下属机构
	     */
	    nic,

	    /**
	     * 机构后缀
	     */
	    nis,

	    /**
	     * 物品名
	     */
	    nm,

	    /**
	     * 化学品名
	     */
	    nmc,

	    /**
	     * 生物名
	     */
	    nb,

	    /**
	     * 动物名
	     */
	    nba,

	    /**
	     * 动物纲目
	     */
	    nbc,

	    /**
	     * 植物名
	     */
	    nbp,

	    /**
	     * 其他专名
	     */
	    nz,

	    /**
	     * 学术词汇
	     */
	    g,

	    /**
	     * 数学相关词汇
	     */
	    gm,

	    /**
	     * 物理相关词汇
	     */
	    gp,

	    /**
	     * 化学相关词汇
	     */
	    gc,

	    /**
	     * 生物相关词汇
	     */
	    gb,

	    /**
	     * 生物类别
	     */
	    gbc,

	    /**
	     * 地理地质相关词汇
	     */
	    gg,

	    /**
	     * 计算机相关词汇
	     */
	    gi,

	    /**
	     * 简称略语
	     */
	    j,

	    /**
	     * 成语
	     */
	    i,

	    /**
	     * 习用语
	     */
	    l,

	    /**
	     * 时间词
	     */
	    t,

	    /**
	     * 时间词性语素
	     */
	    tg,

	    /**
	     * 处所词
	     */
	    s,

	    /**
	     * 方位词
	     */
	    f,

	    /**
	     * 动词
	     */
	    v,

	    /**
	     * 副动词
	     */
	    vd,

	    /**
	     * 名动词
	     */
	    vn,

	    /**
	     * 动词“是”
	     */
	    vshi,

	    /**
	     * 动词“有”
	     */
	    vyou,

	    /**
	     * 趋向动词
	     */
	    vf,

	    /**
	     * 形式动词
	     */
	    vx,

	    /**
	     * 不及物动词（内动词）
	     */
	    vi,

	    /**
	     * 动词性惯用语
	     */
	    vl,

	    /**
	     * 动词性语素
	     */
	    vg,

	    /**
	     * 形容词
	     */
	    a,

	    /**
	     * 副形词
	     */
	    ad,

	    /**
	     * 名形词
	     */
	    an,

	    /**
	     * 形容词性语素
	     */
	    ag,

	    /**
	     * 形容词性惯用语
	     */
	    al,

	    /**
	     * 区别词
	     */
	    b,

	    /**
	     * 区别词性惯用语
	     */
	    bl,

	    /**
	     * 状态词
	     */
	    z,

	    /**
	     * 代词
	     */
	    r,

	    /**
	     * 人称代词
	     */
	    rr,

	    /**
	     * 指示代词
	     */
	    rz,

	    /**
	     * 时间指示代词
	     */
	    rzt,

	    /**
	     * 处所指示代词
	     */
	    rzs,

	    /**
	     * 谓词性指示代词
	     */
	    rzv,

	    /**
	     * 疑问代词
	     */
	    ry,

	    /**
	     * 时间疑问代词
	     */
	    ryt,

	    /**
	     * 处所疑问代词
	     */
	    rys,

	    /**
	     * 谓词性疑问代词
	     */
	    ryv,

	    /**
	     * 代词性语素
	     */
	    rg,

	    /**
	     * 古汉语代词性语素
	     */
	    R,

	    /**
	     * 古汉语代词性语素
	     */
	    Rg,

	    /**
	     * 数词
	     */
	    m,

	    /**
	     * 数量词
	     */
	    mq,

	    M,

	    /**
	     * 甲乙丙丁之类的数词
	     */
	    Mg,

	    /**
	     * 量词
	     */
	    q,

	    /**
	     * 动量词
	     */
	    qv,

	    /**
	     * 时量词
	     */
	    qt,

	    /**
	     * 副词
	     */
	    d,

	    /**
	     * 辄,俱,复之类的副词
	     */
	    dg,

	    /**
	     * 连语
	     */
	    dl,

	    /**
	     * 介词
	     */
	    p,

	    /**
	     * 介词“把”
	     */
	    pba,

	    /**
	     * 介词“被”
	     */
	    pbei,

	    /**
	     * 连词
	     */
	    c,

	    /**
	     * 并列连词
	     */
	    cc,

	    /**
	     * 助词
	     */
	    u,

	    /**
	     * 着
	     */
	    uzhe,

	    /**
	     * 了 喽
	     */
	    ule,

	    /**
	     * 过
	     */
	    uguo,

	    /**
	     * 的 底
	     */
	    ude1,

	    /**
	     * 地
	     */
	    ude2,

	    /**
	     * 得
	     */
	    ude3,

	    /**
	     * 所
	     */
	    usuo,

	    /**
	     * 等 等等 云云
	     */
	    udeng,

	    /**
	     * 一样 一般 似的 般
	     */
	    uyy,

	    /**
	     * 的话
	     */
	    udh,

	    /**
	     * 来讲 来说 而言 说来
	     */
	    uls,

	    /**
	     * 之
	     */
	    uzhi,

	    /**
	     * 连 （“连小学生都会”）
	     */
	    ulian,

	    /**
	     * 叹词
	     */
	    e,

	    /**
	     * 语气词(delete yg)
	     */
	    y,

	    /**
	     * 拟声词
	     */
	    o,

	    /**
	     * 前缀
	     */
	    h,

	    /**
	     * 后缀
	     */
	    k,

	    /**
	     * 字符串
	     */
	    x,

	    /**
	     * 非语素字
	     */
	    xx,

	    /**
	     * 网址URL
	     */
	    xu,

	    /**
	     * 标点符号
	     */
	    w,

	    /**
	     * 左括号，全角：（ 〔  ［  ｛  《 【  〖 〈   半角：( [ { <
	     */
	    wkz,

	    /**
	     * 右括号，全角：） 〕  ］ ｝ 》  】 〗 〉 半角： ) ] { >
	     */
	    wky,

	    /**
	     * 左引号，全角：“ ‘ 『
	     */
	    wyz,

	    /**
	     * 右引号，全角：” ’ 』
	     */
	    wyy,

	    /**
	     * 句号，全角：。
	     */
	    wj,

	    /**
	     * 问号，全角：？ 半角：?
	     */
	    ww,

	    /**
	     * 叹号，全角：！ 半角：!
	     */
	    wt,

	    /**
	     * 逗号，全角：， 半角：,
	     */
	    wd,

	    /**
	     * 分号，全角：； 半角： ;
	     */
	    wf,

	    /**
	     * 顿号，全角：、
	     */
	    wn,

	    /**
	     * 冒号，全角：： 半角： :
	     */
	    wm,

	    /**
	     * 省略号，全角：……  …
	     */
	    ws,

	    /**
	     * 破折号，全角：——   －－   ——－   半角：---  ----
	     */
	    wp,

	    /**
	     * 百分号千分号，全角：％ ‰   半角：%
	     */
	    wb,

	    /**
	     * 单位符号，全角：￥ ＄ ￡  °  ℃  半角：$
	     */
	    wh,

	    /**
	     * 仅用于始##始
	     */
	    end,

	    /**
	     * 仅用于终##终
	     */
	    begin,

	    ;
	}

//	public enum NatureTag
//	{
//		AD, AS, BA, CC, CD, CS, DEC, DEG, DER, DEV, DT, ETC, FW, IJ, JJ, LB, LC, M, 
//		MSP, NN, NN_SHORT, NR_SHORT, NR, NT, NT_SHORT, OD, P, PN, PU, SB, SP, VA, VC, VE, VV, X
//	}
	
//	public enum NatureTag
//	{
//		//名词分为以下子类：
//		n,//名词
//		nr,//人名
//		nr1,//汉语姓氏
//		nr2,//汉语名字
//		nrj,//日语人名
//		nrf,//音译人名
//		ns,//地名
//		nsf,//音译地名
//		nt,//机构团体名
//		nz,//其它专名
//		nl,//名词性惯用语
//		ng,//名词性语素
//		nw,//新词
//		//# 2. 时间词(1个一类，1个二类)
//		t,//时间词
//		tg,//时间词性语素
//		//# 3. 处所词(1个一类)
//		s,//处所词
//		//# 4. 方位词(1个一类)
//		f,//方位词
//		//# 5. 动词(1个一类，9个二类)
//		v,//动词
//		vd,//副动词
//		vn,//名动词
//		vshi,//动词“是”
//		vyou,//动词“有”
//		vf,//趋向动词
//		vx,//形式动词
//		vi,//不及物动词（内动词）
//		vl,//动词性惯用语
//		vg,//动词性语素
//		//# 6. 形容词(1个一类，4个二类)
//		a,//形容词
//		ad,//副形词
//		an,//名形词
//		ag,//形容词性语素
//		al,//形容词性惯用语
//		//# 7. 区别词(1个一类，2个二类)
//		b,//区别词
//		bl,//区别词性惯用语
//		//# 8. 状态词(1个一类)
//		z,//状态词
//		//# 9. 代词(1个一类，4个二类，6个三类)
//		r,//代词
//		rr,//人称代词
//		rz,//指示代词
//		rzt,//时间指示代词
//		rzs,//处所指示代词
//		rzv,//谓词性指示代词
//		ry,//疑问代词
//		ryt,//时间疑问代词
//		rys,//处所疑问代词
//		ryv,//谓词性疑问代词
//		rg,//代词性语素
//		//# 10. 数词(1个一类，1个二类)
//		m,//数词
//		mq,//数量词
//		//# 11. 量词(1个一类，2个二类)
//		q,//量词
//		qv,//动量词
//		qt,//时量词
//		//# 12. 副词(1个一类)
//		d,//副词
//		dl,
//		dg,
//		//# 13. 介词(1个一类，2个二类)
//		p,//介词
//		pba,//介词“把”
//		pbei,//介词“被”
//		//# 14. 连词(1个一类，1个二类)
//		c,//连词
//		cc,//并列连词
//		//# 15. 助词(1个一类，15个二类)
//		u,//助词
//		uzhe,//着
//		ule,//了 喽
//		uguo,//过
//		ude1,//的 底
//		ude2,//地
//		ude3,//得
//		usuo,//所
//		udeng,//等 等等 云云
//		uyy,//一样 一般 似的 般
//		udh,//的话
//		uls,//来讲 来说 而言 说来
//		uzhi,//之
//		ulian,//连 （“连小学生都会”）
//		//# 16. 叹词(1个一类)
//		e,//叹词
//		//# 17. 语气词(1个一类)
//		y,//语气词(delete yg)
//		//# 18. 拟声词(1个一类)
//		o,//拟声词
//		//# 19. 前缀(1个一类)
//		h,//前缀
//		//# 20. 后缀(1个一类)
//		k,// 后缀
//		//# 21. 字符串(1个一类，2个二类)
//		x,// 字符串
//		xx,// 非语素字
//		xu,// 网址URL
//		//# 22. 标点符号(1个一类，16个二类)
//		w,// 标点符号
//		wkz,// 左括号，全角：（ 〔  ［  ｛  《 【  〖〈   半角：( [ { <
//		wky,// 右括号，全角：） 〕  ］ ｝ 》  】 〗 〉 半角： ) ] { >
//		wyz,// 左引号，全角：“ ‘ 『 
//		wyy,// 右引号，全角：” ’ 』
//		wj,// 句号，全角：。
//		ww,// 问号，全角：？ 半角：?
//		wt,// 叹号，全角：！ 半角：!
//		wd,// 逗号，全角：， 半角：,
//		wf,// 分号，全角：； 半角： ;
//		wn,// 顿号，全角：、
//		wm,// 冒号，全角：： 半角： :
//		ws,// 省略号，全角：……  …
//		wp,// 破折号，全角：——   －－   ——－   半角：---  ----
//		wb,// 百分号千分号，全角：％ ‰   半角：%
//		wh,// 单位符号，全角：￥ ＄ ￡  °  ℃  半角：$
//	}
	
	public String convertLabel(NatureTag tag)
    {
		String label = null;
        switch (tag)
        {

            case bg:
                label = "b";
                break;
            case mg:
                label = "Mg";
                break;
            case nx:
                label = "x";
                break;
            case qg:
                label = "q";
                break;
            case ud:
                label = "u";
                break;
            case uj:
                label = "u";
                break;
            case uz:
                label = "uzhe";
                break;
            case ug:
                label = "uguo";
                break;
            case ul:
                label = "ulian";
                break;
            case uv:
                label = "u";
                break;
            case yg:
                label = "y";
                break;
            case zg:
                label = "z";
                break;
            case ntc:
            case ntcf:
            case ntcb:
            case ntch:
            case nto:
            case ntu:
            case nts:
            case nth:
                label = "nt";
                break;
            case nh:
            case nhm:
            case nhd:
                label = "nz";
                break;
            case nn:
                label = "n";
                break;
            case nnt:
                label = "n";
                break;
            case nnd:
                label = "n";
                break;
            case nf:
                label = "n";
                break;
            case ni:
            case nit:
            case nic:
                label = "nt";
                break;
            case nis:
                label = "n";
                break;
            case nm:
                label = "n";
                break;
            case nmc:
                label = "nz";
                break;
            case nb:
                label = "nz";
                break;
            case nba:
                label = "nz";
                break;
            case nbc:
            case nbp:
            case nz:
                label = "nz";
                break;
            case g:
                label = "nz";
                break;
            case gm:
            case gp:
            case gc:
            case gb:
            case gbc:
            case gg:
            case gi:
                label = "nz";
                break;
            case j:
                label = "nz";
                break;
            case i:
                label = "nz";
                break;
            case l:
                label = "nz";
                break;
            case rg:
            case Rg:
                label = "Rg";
                break;
            case udh:
                label = "u";
                break;
            case e:
                label = "y";
                break;
            case xx:
                label = "x";
                break;
            case xu:
                label = "x";
                break;
            case w:
            case wkz:
            case wky:
            case wyz:
            case wyy:
            case wj:
            case ww:
            case wt:
            case wd:
            case wf:
            case wn:
            case wm:
            case ws:
            case wp:
            case wb:
            case wh:
                label = "x";
                break;
            case begin:
                label = "root";
                break;
            default:
                label = tag.toString();
                break;
        }
        return label;
    }

}
