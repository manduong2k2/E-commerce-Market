import React from 'react';
import './Footer.css';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faEnvelope, faPhone } from '@fortawesome/free-solid-svg-icons';
import { faFacebook, faInstagram, faLinkedin } from '@fortawesome/free-brands-svg-icons';

export default function Footer() {
    return (
        <footer className="footer">
            <div className="footer-container">

                {/* Brand */}
                <div className="footer-section">
                    <h2 className="footer-logo">E-Com</h2>
                    <p className="footer-description">
                        Nền tảng thương mại điện tử đa ngành, mang đến trải nghiệm mua sắm nhanh chóng và tiện lợi.
                    </p>
                </div>

                {/* Categories */}
                <div className="footer-section">
                    <h3>Danh mục</h3>
                    <ul>
                        <li>Điện tử</li>
                        <li>Thời trang</li>
                        <li>Gia dụng</li>
                        <li>Sách</li>
                    </ul>
                </div>

                {/* Support */}
                <div className="footer-section">
                    <h3>Hỗ trợ</h3>
                    <ul>
                        <li>Trung tâm trợ giúp</li>
                        <li>Chính sách đổi trả</li>
                        <li>Giao hàng</li>
                        <li>Bảo mật</li>
                    </ul>
                </div>

                {/* Contact */}
                <div className="footer-section">
                    <h3>Liên hệ</h3>

                    <p>
                        <FontAwesomeIcon icon={faEnvelope} className="icon" />
                        support@ecom.com
                    </p>

                    <p>
                        <FontAwesomeIcon icon={faPhone} className="icon" />
                        0123 456 789
                    </p>

                    <div className="footer-socials">
                        <span><FontAwesomeIcon icon={faFacebook} /></span>
                        <span><FontAwesomeIcon icon={faInstagram} /></span>
                        <span><FontAwesomeIcon icon={faLinkedin} /></span>
                    </div>
                </div>

            </div>

            <div className="footer-bottom">
                © {new Date().getFullYear()} E-Com. All rights reserved.
            </div>
        </footer>
    );
}