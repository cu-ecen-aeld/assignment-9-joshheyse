# See https://git.yoctoproject.org/poky/tree/meta/files/common-licenses
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

inherit module
inherit update-rc.d

EXTRA_OEMAKE:append:task-install = " -C ${STAGING_KERNEL_DIR} M=${S}"
EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR}"

INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME:${PN} = "ldd-aesd-char"

SRC_URI = "git://git@github.com/cu-ecen-aeld/assignments-3-and-later-joshheyse.git;protocol=ssh;branch=master \
           file://ldd-aesd-char"

PV = "1.0+git${SRCPV}"
SRCREV = "67db054ac2ec7f205afa6990a7ae41eccc7700ff"

S = "${WORKDIR}/git/aesd-char-driver"

FILES:${PN} += "${bindir}/aesdchar_load"
FILES:${PN} += "${bindir}/aesdchar_unload"
FILES:${PN} += "${sysconfdir}/init.d/ldd-aesd-char"

do_install:append () {
  install -d ${D}${bindir}
  install -m 0755 ${S}/aesdchar_load ${D}${bindir}/
  install -m 0755 ${S}/aesdchar_unload ${D}${bindir}/

  install -d ${D}${sysconfdir}/init.d
  install -m 0744 ${WORKDIR}/ldd-aesd-char ${D}${sysconfdir}/init.d/

  install -d ${D}/lib/modules/${KERNEL_VERSION}/extra
  install -m 0755 ${S}/aesdchar.ko ${D}/lib/modules/${KERNEL_VERSION}/extra/
}

